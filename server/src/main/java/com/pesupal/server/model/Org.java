package com.pesupal.server.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Org {

    @Id
    @SequenceGenerator(name = "org_id_seq", sequenceName = "org_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "org_id_seq")
    private Long orgId;

    private String name;

    private String displayName;

    private String description;

    private LocalDateTime createdAt;

    private Long premiumId;
}
