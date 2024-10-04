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
public class Premium {

    @Id
    private Long premiumId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long renewalId;
}
