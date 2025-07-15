package com.pesupal.server.model.org;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.payment.Transaction;
import com.pesupal.server.model.subscription.SubscriptionPlan;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class OrgSubscriptionHistory extends CreationTimeAuditable {

    @ManyToOne
    private Org org;

    @ManyToOne
    private SubscriptionPlan subscriptionPlan;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @ManyToOne
    private Transaction transaction;
}
