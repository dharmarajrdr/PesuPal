package com.pesupal.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrgAction {

    ADD_MEMBER(1, "Add Member"),
    REMOVE_MEMBER(2, "Remove Member"),
    UPDATE_MEMBER(3, "Update Member"),

    LEAVE_ORG(4, "Leave Org"),
    DELETE_ORG(5, "Delete Org"),
    UPDATE_ORG(6, "Update Org"),

    CREATE_POST(7, "Create Post"),
    ATTACH_MEDIA_IN_POST(8, "Attach Media in Post"),

    CREATE_GROUP(9, "Create Group"),
    CREATE_ORG_ROLE(10, "Create Role"),

    CREATE_DEPARTMENT(11, "Create Department"),
    UPDATE_DEPARTMENT(12, "Update Department"),

    ACCESS_STORE(13, "Access Store");

    private final int actionId;
    private final String title;
}
