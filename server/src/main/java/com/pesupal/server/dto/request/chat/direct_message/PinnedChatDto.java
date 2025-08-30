package com.pesupal.server.dto.request.chat.direct_message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.chat.direct_message.DirectMessageChat;
import com.pesupal.server.model.chat.direct_message.PinnedDirectMessage;
import com.pesupal.server.model.chat.group_message.Group;
import com.pesupal.server.model.chat.group_message.GroupChatPinned;
import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

import java.net.URL;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PinnedChatDto {

    private Long id;

    private String displayName;

    private URL displayPicture;

    private String status;

    private String chatId;

    public static PinnedChatDto fromPinnedDirectMessage(PinnedDirectMessage pinnedDirectMessage) {

        DirectMessageChat pinnedChat = pinnedDirectMessage.getChat();
        OrgMember pinnedByUser = pinnedDirectMessage.getPinnedBy();
        OrgMember pinnedUser = pinnedChat.getAnotherUser(pinnedByUser);

        PinnedChatDto dto = new PinnedChatDto();
        dto.setDisplayName(pinnedUser.getDisplayName());
        dto.setStatus(pinnedUser.getStatus());
        dto.setChatId(pinnedChat.getPublicId());
        dto.setId(pinnedDirectMessage.getId());
        return dto;
    }

    public static PinnedChatDto fromUserAndOrgMemberAndPinnedGroupChatMessage(GroupChatPinned groupChatPinned) {

        PinnedChatDto dto = new PinnedChatDto();
        Group group = groupChatPinned.getGroup();
        dto.setDisplayName(group.getName());
        dto.setChatId(group.getPublicId());
        dto.setId(groupChatPinned.getId());
        return dto;
    }
}
