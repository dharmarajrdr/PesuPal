package com.pesupal.server.dto.response.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.enums.ChatMode;
import com.pesupal.server.enums.MessageType;
import com.pesupal.server.enums.Reaction;
import com.pesupal.server.enums.ReadReceipt;
import com.pesupal.server.model.chat.direct_message.DirectMessage;
import com.pesupal.server.model.chat.direct_message.DirectMessageChat;
import com.pesupal.server.model.chat.group_message.GroupChatMessage;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDto {

    private Long id;

//    private String orgId;

    private LocalDateTime createdAt;

    private UserPreviewDto sender;

    private String chatId;

    private String receiverId;

    private String message;

    private Boolean deleted;

    private ReadReceipt readReceipt;

    private Map<Reaction, Integer> reactions;

    private MediaFileDto media;

    private ChatMode chatMode;

    private MessageType messageType;

    public static MessageDto fromDirectMessage(DirectMessage directMessage) {

        MessageDto responseDto = new MessageDto();
        responseDto.setId(directMessage.getId());
        if (!directMessage.isDeleted()) {
            responseDto.setMessage(directMessage.getMessage()); // Only set message if not deleted
        }
//        responseDto.setOrgId(directMessage.getOrg().getPublicId());
        responseDto.setCreatedAt(directMessage.getCreatedAt());
        DirectMessageChat directMessageChat = directMessage.getDirectMessageChat();
        responseDto.setChatId(directMessageChat.getPublicId());
        responseDto.setDeleted(directMessage.isDeleted());
        responseDto.setReadReceipt(directMessage.getReadReceipt());
        responseDto.setChatMode(ChatMode.DIRECT_MESSAGE);
        responseDto.setMessageType(directMessage.getMessageType());
        responseDto.setReceiverId(directMessageChat.getAnotherUser(directMessage.getSender()).getPublicId());
        return responseDto;
    }

    public static MessageDto fromGroupMessage(GroupChatMessage groupChatMessage) {

        MessageDto responseDto = new MessageDto();
        responseDto.setId(groupChatMessage.getId());
        if (!groupChatMessage.isDeleted()) {
            responseDto.setMessage(groupChatMessage.getMessage()); // Only set message if not deleted
        }
//        responseDto.setOrgId(groupChatMessage.getGroup().getOrg().getPublicId());
        responseDto.setCreatedAt(groupChatMessage.getCreatedAt());
        responseDto.setChatId(groupChatMessage.getGroup().getPublicId());
        responseDto.setDeleted(groupChatMessage.isDeleted());
        responseDto.setChatMode(ChatMode.GROUP_MESSAGE);
        responseDto.setMessageType(groupChatMessage.getMessageType());
        return responseDto;
    }
}
