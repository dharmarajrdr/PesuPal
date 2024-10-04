package com.pesupal.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FeatureLimitations {

    @Id
    private Long id;

    private Long subscriptionId;

    private Integer maxUserPerOrg;

    private Integer maxOwnersPerOrg;

    private Integer maxMaintainersPerOrg;

    private Integer maxCreateChannelsPerOrg;

    private Integer maxCreateGroupPerOrg;

    private Integer maxMediaUploadPerFeed;

    private Integer maxMediaUploadPerChat;
}
