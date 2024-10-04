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
public class Org {

    @Id
    private Long orgId;

    private String name;

    private String displayName;

    private String description;

    private LocalDateTime createdAt;

    private Long premiumId;
}
