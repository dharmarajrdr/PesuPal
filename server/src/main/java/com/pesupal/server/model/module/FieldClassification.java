package com.pesupal.server.model.module;

import lombok.Getter;

@Getter
public enum FieldClassification {

    SYSTEM_FIELD("These fields will be available in all modules and records."),
    USER_DEFINED_FIELD("These fields are created by user for their use-case");

    private final String description;

    FieldClassification(String description) {
        this.description = description;
    }
}
