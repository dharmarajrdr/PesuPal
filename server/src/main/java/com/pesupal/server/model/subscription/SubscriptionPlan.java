package com.pesupal.server.model.subscription;

import com.pesupal.server.dto.request.PaymentDto;
import com.pesupal.server.enums.Currency;
import com.pesupal.server.enums.Interval;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.payment.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Entity
public class SubscriptionPlan extends CreationTimeAuditable {

    @Column(unique = true, nullable = false)
    private String code;

    private String name;

    @Column(nullable = false)
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Interval interval;

    private String description;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private Long numberOfDays;
    
    private String badge;

    public PaymentDto toPaymentDto() {
        if (!active) {
            throw new ActionProhibitedException("Subscription plan is not active");
        }
        return PaymentDto.builder().name(code).amount(price * 100).currency(currency).interval(interval).description(description).build();
    }

    public Transaction toTransaction() {

        Transaction transaction = new Transaction();
        transaction.setAmount(price);
        transaction.setCurrency(currency);
        return transaction;
    }

}
