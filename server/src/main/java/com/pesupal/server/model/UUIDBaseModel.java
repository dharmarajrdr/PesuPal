package com.pesupal.server.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
public class UUIDBaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
