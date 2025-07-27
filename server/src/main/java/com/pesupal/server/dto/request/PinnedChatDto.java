package com.pesupal.server.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.chat.DirectMessageChat;
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

    public static PinnedChatDto fromPinnedDirectMessage(PinnedDirectMessage pinnedDirectMessage) {

        DirectMessageChat pinnedChat = pinnedDirectMessage.getChat();
        OrgMember pinnedByUser = pinnedDirectMessage.getPinnedBy();
        OrgMember pinnedUser = pinnedChat.getAnotherUser(pinnedByUser);

        PinnedChatDto dto = new PinnedChatDto();
        dto.setDisplayName(pinnedUser.getDisplayName());
        dto.setDisplayPicture(pinnedUser.getDisplayPicture());
        dto.setStatus(pinnedUser.getStatus());
        dto.setChatId(pinnedChat.getPublicId());
        dto.setId(pinnedDirectMessage.getId());
        return dto;
    }

    public static PinnedChatDto fromUserAndOrgMemberAndPinnedGroupChatMessage(GroupChatPinned groupChatPinned) {

        PinnedChatDto dto = new PinnedChatDto();
        Group group = groupChatPinned.getGroup();
        dto.setDisplayName(group.getName());
        dto.setDisplayPicture(group.getDisplayPicture());
        dto.setChatId(group.getPublicId());
        dto.setId(groupChatPinned.getId());
        return dto;
    }
}
