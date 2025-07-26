package com.pesupal.server.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.chat.PinnedDirectMessage;
import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatPinned;
import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PinnedChatDto {

    private Long id;

    private String displayName;

    private String displayPicture;

    private String status;

    private String chatId;

    public static PinnedChatDto fromUserAndOrgMemberAndPinnedDirectMessage(OrgMember currentOrgMember, OrgMember orgMember, PinnedDirectMessage pinnedDirectMessage) {

        PinnedChatDto dto = new PinnedChatDto();
        dto.setDisplayName(orgMember.getDisplayName());
        dto.setDisplayPicture(orgMember.getDisplayPicture());
        dto.setStatus(orgMember.getStatus());
//        dto.setChatId(Chat.getChatId(orgMember.getPublicId(), currentOrgMember.getPublicId(), orgMember.getOrg().getId()));
        dto.setId(pinnedDirectMessage.getId());
        return dto;
    }

    public static PinnedChatDto fromUserAndOrgMemberAndPinnedGroupChatMessage(GroupChatPinned groupChatPinned) {

        PinnedChatDto dto = new PinnedChatDto();
        Group group = groupChatPinned.getGroup();
        dto.setDisplayName(group.getName());
        dto.setDisplayPicture(group.getDisplayPicture());
        dto.setChatId(group.getId().toString());
        dto.setId(groupChatPinned.getId());
        return dto;
    }
}
