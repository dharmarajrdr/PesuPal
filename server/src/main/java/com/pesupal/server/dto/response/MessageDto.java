package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    private LocalDateTime createdAt;

    private Long sender;

    private String message;

    private Boolean deleted;

    private ReadReceipt readReceipt;

    private Map<Reaction, Integer> reactions;

    private MediaFileDto media;

    public static MessageDto fromDirectMessage(DirectMessage directMessage) {

        MessageDto responseDto = new MessageDto();
        responseDto.setId(directMessage.getId());
        responseDto.setSender(directMessage.getSender().getId());
        if (!directMessage.isDeleted()) {
            responseDto.setMessage(directMessage.getMessage()); // Only set message if not deleted
        }
        responseDto.setCreatedAt(directMessage.getCreatedAt());
        responseDto.setDeleted(directMessage.isDeleted());
        responseDto.setReadReceipt(directMessage.getReadReceipt());
        return responseDto;
    }

    public static MessageDto fromGroupMessage(GroupChatMessage groupChatMessage) {

        MessageDto responseDto = new MessageDto();
        responseDto.setId(groupChatMessage.getId());
        responseDto.setSender(groupChatMessage.getSender().getId());
        if (!groupChatMessage.isDeleted()) {
            responseDto.setMessage(groupChatMessage.getMessage()); // Only set message if not deleted
        }
        responseDto.setCreatedAt(groupChatMessage.getCreatedAt());
        responseDto.setDeleted(groupChatMessage.isDeleted());
        return responseDto;
    }
}
