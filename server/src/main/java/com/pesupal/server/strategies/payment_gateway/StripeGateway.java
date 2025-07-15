package com.pesupal.server.strategies.payment_gateway;

import com.pesupal.server.dto.response.WebhookDto;
import com.pesupal.server.service.interfaces.PaymentGateway;
import com.pesupal.server.service.interfaces.SubscriptionService;
import com.pesupal.server.service.interfaces.WebhookService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.*;
import com.stripe.param.PriceCreateParams.Recurring.Interval;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Primary
public class StripeGateway implements PaymentGateway, WebhookService, SubscriptionService {

    @Value("${stripe.api-key}")
    private String API_KEY = "";

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
    public String generatePaymentLink() throws StripeException {

        Stripe.apiKey = API_KEY;
        Price price = getPrice();

        PaymentLinkCreateParams params = PaymentLinkCreateParams.builder()
                .addLineItem(
                        PaymentLinkCreateParams.LineItem.builder()
                                .setPrice(price.getId())
                                .setQuantity(1L)
                                .build())
                .setAfterCompletion(
                        PaymentLinkCreateParams.AfterCompletion.builder()
                                .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                .setRedirect(
                                        PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                .setUrl("https://www.google.com/?txn_id=abcd1234").build())
                                .build())
                .build();

        PaymentLink paymentLink = PaymentLink.create(params);
        return paymentLink.getUrl();
    }

    public Price getPrice() throws StripeException {

        PriceCreateParams params = PriceCreateParams.builder()
                .setCurrency("inr")
                .setUnitAmount(70000L)
                .setProductData(
                        PriceCreateParams.ProductData.builder()
                                .setName("Boult Audio")
                                .build())
                .build();

        return Price.create(params);
    }

    @Override
    public WebhookDto createWebhook(String url, List<String> events) {

        try {
            Stripe.apiKey = API_KEY;
            WebhookEndpointCreateParams.Builder builder = WebhookEndpointCreateParams.builder().setUrl(url);
            for (String event : events) {
                builder.addEnabledEvent(WebhookEndpointCreateParams.EnabledEvent.valueOf(event));
            }
            WebhookEndpointCreateParams params = builder.build();
            WebhookEndpoint webhookEndpoint = WebhookEndpoint.create(params);
            return WebhookDto.fromWebhookEndpoint(webhookEndpoint);
        } catch (StripeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

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

    public String SubscriptionService(String customerName, String customerEmail, Long productAmount, String productName,
            PlanCreateParams.Interval interval) {

        Stripe.apiKey = API_KEY;

        try {
            CustomerCreateParams customerParams = CustomerCreateParams.builder()
                    .setName(customerName)
                    .setEmail(customerEmail)
                    .build();
            Customer customer = Customer.create(customerParams);

            ProductCreateParams productParams = ProductCreateParams.builder()
                    .setName(productName)
                    .build();
            Product product = Product.create(productParams);

            PriceCreateParams priceParams = PriceCreateParams.builder()
                    .setUnitAmount(productAmount)
                    .setCurrency("usd")
                    .setRecurring(
                            PriceCreateParams.Recurring.builder()
                                    .setInterval(from(interval))
                                    .build())
                    .setProduct(product.getId())
                    .build();

            Price price = Price.create(priceParams);

            Map<String, String> metadata = new HashMap<>();
            metadata.put("product_name", productName);

            SubscriptionCreateParams subParams = SubscriptionCreateParams.builder()
                    .setCustomer(customer.getId())
                    .addItem(
                            SubscriptionCreateParams.Item.builder()
                                    .setPrice(price.getId())
                                    .build())
                    .setTrialPeriodDays(trialDays)
                    .build();

            Subscription subscription = Subscription.create(subParams);
            return subscription.getId();
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    private Interval from(PlanCreateParams.Interval interval) {
        switch (interval) {
            case DAY:
                return Interval.DAY;
            case WEEK:
                return Interval.WEEK;
            case MONTH:
                return Interval.MONTH;
            case YEAR:
                return Interval.YEAR;
            default:
                throw new IllegalArgumentException("Unknown interval: " + interval);
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
    @Override
    public String createSubscriptionForProduct(String customerName, String customerEmail, Long productAmount,
            String productName, PlanCreateParams.Interval interval) {

        return "";
    }
}
