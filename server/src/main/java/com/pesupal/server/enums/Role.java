package com.pesupal.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    SUPER_ADMIN(3),
    ADMIN(2),
    USER(1);

    private final int level;
}
