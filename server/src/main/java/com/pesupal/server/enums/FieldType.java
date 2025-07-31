package com.pesupal.server.enums;

import lombok.Getter;

import java.util.List;

@Getter
public enum FieldType {

    STRING(1, List.of(FilterOperation.EQUALS, FilterOperation.NOT_EQUALS, FilterOperation.CONTAINS, FilterOperation.NOT_CONTAINS, FilterOperation.STARTS_WITH, FilterOperation.ENDS_WITH)),
    INTEGER(2, List.of(FilterOperation.EQUALS, FilterOperation.NOT_EQUALS, FilterOperation.GREATER_THAN, FilterOperation.LESS_THAN, FilterOperation.GREATER_THAN_OR_EQUAL_TO, FilterOperation.LESS_THAN_OR_EQUAL_TO, FilterOperation.BETWEEN, FilterOperation.IS_NULL, FilterOperation.IS_NOT_NULL)),
    DECIMAL(3),
    DAY(4),
    DATE_TIME(5),
    SELECT(6),
    MULTI_SELECT(7),
    USER(8),
    EMAIL(9),
    PHONE(10),
    CURRENCY(11),
    GEO_LOCATION(12),
    LINK(13),
    FILE(14),
    TEXT(15),
    CHECKBOX(16),
    TRANSITION(17),
    RELATION(18),
    PERCENT(19);

    private final int fieldId;
    private final List<FilterOperation> operations;

    FieldType(int fieldId) {
        this.fieldId = fieldId;
        this.operations = List.of();
    }

    FieldType(int fieldId, List<FilterOperation> operations) {
        this.fieldId = fieldId;
        this.operations = operations;
    }
}
