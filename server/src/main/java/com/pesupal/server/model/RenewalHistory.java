package com.pesupal.server.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RenewalHistory {

    @Id
    private Long renewalId;

    private Long orgId;

    private LocalDateTime renewedOn;

    private Long subscriptionId;
}
