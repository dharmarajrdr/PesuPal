package com.pesupal.server.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.UUID;

@Data
@MappedSuperclass
public class UUIDBaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
