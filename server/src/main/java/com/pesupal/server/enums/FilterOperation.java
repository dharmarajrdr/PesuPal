package com.pesupal.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FilterOperation {

    EQUALS("equals", "="),
    NOT_EQUALS("not equals", "!="),
    CONTAINS("contains", "LIKE"),
    NOT_CONTAINS("does not contain", "NOT LIKE"),
    STARTS_WITH("starts with", "LIKE"),
    ENDS_WITH("ends with", "LIKE"),
    GREATER_THAN("greater than", ">"),
    LESS_THAN("less than", "<"),
    GREATER_THAN_OR_EQUAL_TO("greater than or equal to", ">="),
    LESS_THAN_OR_EQUAL_TO("less than or equal to", "<="),
    IN("in", "IN"),
    NOT_IN("not in", "NOT IN"),
    IS_NULL("is null", "IS NULL"),
    IS_NOT_NULL("is not null", "IS NOT NULL"),
    BETWEEN("between", "BETWEEN"),
    NOT_BETWEEN("not between", "NOT BETWEEN"),
    BEFORE("before", "<"),
    AFTER("after", ">");

    private final String displayName;
    private final String sqlOperator;

}
