package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.ChatMode;
import com.pesupal.server.enums.Reaction;
import com.pesupal.server.enums.ReadReceipt;
import com.pesupal.server.model.chat.DirectMessage;
import com.pesupal.server.model.group.GroupChatMessage;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDto {

    private Long id;

    private Long orgId;

    private LocalDateTime createdAt;

    private UserPreviewDto sender;

    private String chatId;

    private Long receiverId;

    private String message;

    private Boolean deleted;

    private ReadReceipt readReceipt;

    private Map<Reaction, Integer> reactions;

    private MediaFileDto media;

    private ChatMode chatMode;

    public static MessageDto fromDirectMessage(DirectMessage directMessage) {

        MessageDto responseDto = new MessageDto();
        responseDto.setId(directMessage.getId());
        if (!directMessage.isDeleted()) {
            responseDto.setMessage(directMessage.getMessage()); // Only set message if not deleted
        }
        responseDto.setOrgId(directMessage.getOrg().getId());
        responseDto.setCreatedAt(directMessage.getCreatedAt());
        responseDto.setChatId(directMessage.getChatId());
        responseDto.setDeleted(directMessage.isDeleted());
        responseDto.setReadReceipt(directMessage.getReadReceipt());
        responseDto.setChatMode(ChatMode.DIRECT_MESSAGE);
        Long[] parsedChatId = Chat.parseChatId(directMessage.getChatId());
        Long receiverId = parsedChatId[0].equals(directMessage.getSender().getId()) ? parsedChatId[1] : parsedChatId[0];
        responseDto.setReceiverId(receiverId);
        return responseDto;
    }

    public static MessageDto fromGroupMessage(GroupChatMessage groupChatMessage) {

        MessageDto responseDto = new MessageDto();
        responseDto.setId(groupChatMessage.getId());
        if (!groupChatMessage.isDeleted()) {
            responseDto.setMessage(groupChatMessage.getMessage()); // Only set message if not deleted
        }
        responseDto.setOrgId(groupChatMessage.getGroup().getOrg().getId());
        responseDto.setCreatedAt(groupChatMessage.getCreatedAt());
        responseDto.setChatId(Long.toString(groupChatMessage.getGroup().getId()));
        responseDto.setDeleted(groupChatMessage.isDeleted());
        responseDto.setChatMode(ChatMode.GROUP_MESSAGE);
        return responseDto;
    }
}
