package com.pesupal.server.model.module;

import com.pesupal.server.enums.FieldType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PredefinedField {

    CREATED_AT("Created At", "created_at", FieldType.DATE_TIME, false),
    UPDATED_AT("Updated At", "updated_at", FieldType.DATE_TIME, false),
    CREATED_BY("Created By", "created_by", FieldType.USER, false),
    UPDATED_BY("Updated By", "updated_by", FieldType.USER, false),
    SUBJECT("Subject", "subject", FieldType.STRING, true),
    DESCRIPTION("Description", "description", FieldType.STRING, true),
    OWNER("Owner", "owner", FieldType.USER, true);

    private final String displayName;
    private final String fieldName;
    private final FieldType fieldType;
    private final boolean isEditable;
}
