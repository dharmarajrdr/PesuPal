package com.pesupal.server.model.module;

public enum ModuleAccessibility {

    ANYONE_IN_ORG("Anyone in the organization can access"),
    SELECTIVE_MEMBERS("Only module members can access"),
    ONLY_ME("Only you can access");

    private final String description;

    ModuleAccessibility(String description) {
        this.description = description;
    }
}
