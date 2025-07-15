package com.pesupal.server.model.payment;

import com.pesupal.server.enums.Currency;
import com.pesupal.server.enums.PaymentStatus;
import com.pesupal.server.enums.SupportedGateway;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Transaction extends CreationTimeAuditable {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SupportedGateway gateway;

    @ManyToOne
    private Org org;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false, unique = true)
    private String paymentLink;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.INITIATED;

    @Column(nullable = false)
    private Currency currency;
}
