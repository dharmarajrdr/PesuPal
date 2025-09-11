package com.pesupal.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Workspace {

    ORG_SPACE("org", "Org Space"),
    TEAM_SPACE("team", "Team Space"),
    PERSONAL_SPACE("personal", "Personal Space");

    private final String value;
    private final String displayName;
}
