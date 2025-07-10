package com.pesupal.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Workspace {

    ORG_SPACE("org"),
    TEAM_SPACE("team"),
    PERSONAL_SPACE("personal");

    private final String value;
}
