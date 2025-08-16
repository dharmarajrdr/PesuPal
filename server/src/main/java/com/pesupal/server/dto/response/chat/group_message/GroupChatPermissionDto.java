package com.pesupal.server.dto.response.chat.group_message;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupChatPermissionDto {

    private String name;

    private boolean superAdmin;

    private boolean admin;

    private boolean user;

    public GroupChatPermissionDto(String name) {

        this.name = name;
    }
}
