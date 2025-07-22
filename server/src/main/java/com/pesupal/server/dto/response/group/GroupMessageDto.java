package com.pesupal.server.dto.response.group;

import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.model.group.GroupChatMessage;
import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

@Data
public class GroupMessageDto {

    private Long id;

    private String message;

    private UserPreviewDto sender;

    public static GroupMessageDto fromGroupMessage(GroupChatMessage groupChatMessage) {
        GroupMessageDto groupMessageDto = new GroupMessageDto();
        groupMessageDto.setId(groupChatMessage.getId());
        groupMessageDto.setMessage(groupChatMessage.getMessage());
        return groupMessageDto;
    }

    public static GroupMessageDto fromGroupMessageAndOrgMember(GroupChatMessage groupChatMessage, OrgMember orgMember) {
        GroupMessageDto groupMessageDto = fromGroupMessage(groupChatMessage);
        groupMessageDto.setSender(UserPreviewDto.fromOrgMember(orgMember));
        return groupMessageDto;
    }
}
