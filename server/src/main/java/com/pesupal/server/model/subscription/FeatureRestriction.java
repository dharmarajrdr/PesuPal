package com.pesupal.server.model.subscription;

import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class FeatureRestriction extends BaseModel {

    @OneToOne
    private SubscriptionPlan subscriptionPlan;

    private String maxMembers;

    private String maxGroups;

    private String maxAdmins;

    private String maxMediaSize;
}
