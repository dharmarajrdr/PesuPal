package com.pesupal.server.dto.request.chat.group_message;

import lombok.Data;

@Data
public class AddGroupMemberDto {

    private String groupId;

    private String userId;
}
