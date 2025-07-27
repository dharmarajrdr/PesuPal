package com.pesupal.server.dto.request.group;

import lombok.Data;

@Data
public class AddGroupMemberDto {

    private String groupId;

    private String userId;
}
