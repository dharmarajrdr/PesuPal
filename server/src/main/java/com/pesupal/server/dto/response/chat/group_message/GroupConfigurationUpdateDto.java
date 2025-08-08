package com.pesupal.server.dto.response.chat.group_message;

import com.pesupal.server.enums.Role;
import lombok.Data;

@Data
public class GroupConfigurationUpdateDto {

    private String groupId;

    private Role role;

    private String name;

    private boolean enable;
}
