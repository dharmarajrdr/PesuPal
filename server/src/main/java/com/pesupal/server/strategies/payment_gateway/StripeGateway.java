package com.pesupal.server.strategies.payment_gateway;

import com.pesupal.server.dto.request.PaymentDto;
import com.pesupal.server.dto.response.WebhookDto;
import com.pesupal.server.service.interfaces.PaymentGateway;
import com.pesupal.server.service.interfaces.WebhookService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.*;
import com.stripe.param.PriceCreateParams.Recurring.Interval;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Primary
public class StripeGateway implements PaymentGateway, WebhookService {

    Dotenv dotenv = Dotenv.load();

    private String API_KEY = dotenv.get("stripe.api-key");

    private final Long trialDays = 730L;

    /**
     * Things needed are as follows.
     * <ol>
     * <li>Amount</li>
     * <li>Redirect URL</li>
     * <li>API_KEY</li>
     * </ol>
     *
     * @return
     */
    @Override
    public String generatePaymentLink(PaymentDto paymentDto) throws StripeException {

        Stripe.apiKey = API_KEY;
        Price price = getPrice(paymentDto);

        PaymentLinkCreateParams params = PaymentLinkCreateParams.builder().addLineItem(
                        PaymentLinkCreateParams.LineItem.builder()
                                .setPrice(price.getId())
                                .setQuantity(1L)
                                .build())
                .setAfterCompletion(
                        PaymentLinkCreateParams.AfterCompletion.builder()
                                .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                .setRedirect(
                                        PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                .setUrl("https://www.google.com/?txn_id=abcd1234")
                                                .build())
                                .build())
                .build();

        PaymentLink paymentLink = PaymentLink.create(params);
        return paymentLink.getUrl();
    }

    /**
     * Creates a price for a product.
     *
     * @return
     * @throws StripeException
     */
    public Price getPrice(PaymentDto paymentDto) throws StripeException {

        PriceCreateParams params = PriceCreateParams.builder()
                .setCurrency(paymentDto.getCurrency().name().toLowerCase())
                .setUnitAmount(paymentDto.getAmount())
                .setProductData(
                        PriceCreateParams.ProductData.builder()
                                .setName(paymentDto.getName())
                                .build())
                .build();

        return Price.create(params);
    }

    /**
     * Creates a webhook endpoint for Stripe.
     *
     * @param url
     * @param events
     * @return
     */
    @Override
    public WebhookDto createWebhook(String url, List<String> events) {

        try {
            Stripe.apiKey = API_KEY;
            WebhookEndpointCreateParams.Builder builder = WebhookEndpointCreateParams.builder().setUrl(url);
            for (WebhookEndpointCreateParams.EnabledEvent enabledEvent : WebhookEndpointCreateParams.EnabledEvent.values()) {
                if (events.contains(enabledEvent.getValue())) {
                    builder.addEnabledEvent(enabledEvent);
                }
            }
            WebhookEndpointCreateParams params = builder.build();
            WebhookEndpoint webhookEndpoint = WebhookEndpoint.create(params);
            return WebhookDto.fromWebhookEndpoint(webhookEndpoint);
        } catch (StripeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Deletes a webhook endpoint by its ID.
     *
     * @param webhookId
     * @return
     */
    @Override
    public Boolean deleteWebhook(String webhookId) {

        try {
            Stripe.apiKey = API_KEY;
            WebhookEndpoint resource = WebhookEndpoint.retrieve(webhookId);
            WebhookEndpoint webhookEndpoint = resource.delete();
            return webhookEndpoint.getDeleted();
        } catch (StripeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Updates a webhook endpoint with a new URL and events.
     *
     * @param updatedUrl
     * @param events
     * @param webhookId
     * @return
     */
    @Override
    public WebhookDto updateWebhook(String updatedUrl, List<String> events, String webhookId) {

        try {
            Stripe.apiKey = API_KEY;
            WebhookEndpoint resource = WebhookEndpoint.retrieve(webhookId);
            WebhookEndpointUpdateParams.Builder builder = WebhookEndpointUpdateParams.builder().setUrl(updatedUrl);
            for (String event : events) {
                builder.addEnabledEvent(WebhookEndpointUpdateParams.EnabledEvent.valueOf(event));
            }
            WebhookEndpointUpdateParams params = builder.build();
            WebhookEndpoint webhookEndpoint = resource.update(params);
            return WebhookDto.fromWebhookEndpoint(webhookEndpoint);
        } catch (StripeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Creates a subscription for a product with the given parameters.
     *
     * @param customerName
     * @param customerEmail
     * @param productAmount
     * @param productName
     * @param interval
     * @return
     */
    public String SubscriptionService(String customerName, String customerEmail, Long productAmount, String productName,
                                      PlanCreateParams.Interval interval) {

        Stripe.apiKey = API_KEY;

        try {

            CustomerCreateParams customerParams = CustomerCreateParams.builder().setName(customerName)
                    .setEmail(customerEmail).build();
            Customer customer = Customer.create(customerParams);

            ProductCreateParams productParams = ProductCreateParams.builder().setName(productName).build();
            Product product = Product.create(productParams);

            PriceCreateParams priceParams = PriceCreateParams.builder()
                    .setUnitAmount(productAmount)
                    .setCurrency("usd")
                    .setRecurring(
                            PriceCreateParams.Recurring.builder()
                                    .setInterval(from(interval))
                                    .build())
                    .setProduct(product.getId()).build();

            Price price = Price.create(priceParams);

            Map<String, String> metadata = new HashMap<>();
            metadata.put("product_name", productName);

            SubscriptionCreateParams subParams = SubscriptionCreateParams.builder()
                    .setCustomer(customer.getId())
                    .addItem(
                            SubscriptionCreateParams.Item.builder()
                                    .setPrice(price.getId())
                                    .build())
                    .setTrialPeriodDays(trialDays).build();

            Subscription subscription = Subscription.create(subParams);
            return subscription.getId();
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    private Interval from(PlanCreateParams.Interval interval) {
        return switch (interval) {
            case DAY -> Interval.DAY;
            case WEEK -> Interval.WEEK;
            case MONTH -> Interval.MONTH;
            case YEAR -> Interval.YEAR;
        };
    }

}
