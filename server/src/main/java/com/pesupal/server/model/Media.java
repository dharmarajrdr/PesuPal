package com.pesupal.server.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class Media extends BaseModel {

    private UUID publicId;

    private String extension;
}
