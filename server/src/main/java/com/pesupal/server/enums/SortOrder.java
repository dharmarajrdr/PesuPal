package com.pesupal.server.enums;

public enum SortOrder {

    ASC, DESC;

    public boolean isAscending() {
        return this == ASC;
    }

    public boolean isDescending() {
        return this == DESC;
    }
}
