package com.pesupal.server.dto.request;

import com.pesupal.server.helpers.Chat;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import lombok.Data;

@Data
public class PinnedDirectMessageDto {

    private String displayName;

    private String displayPicture;

    private String status;

    private String chatId;

    public static PinnedDirectMessageDto fromUserAndOrgMember(User user, OrgMember orgMember) {

        PinnedDirectMessageDto dto = new PinnedDirectMessageDto();
        dto.setDisplayName(orgMember.getDisplayName());
        dto.setDisplayPicture(orgMember.getDisplayPicture());
        dto.setStatus(orgMember.getStatus());
        dto.setChatId(Chat.getChatId(orgMember.getUser().getId(), user.getId(), orgMember.getOrg().getId()));
        return dto;
    }
}
