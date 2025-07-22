package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.Reaction;
import com.pesupal.server.enums.ReadReceipt;
import com.pesupal.server.model.chat.DirectMessage;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DirectMessageResponseDto {

    private Long id;

    private LocalDateTime createdAt;

    private Long sender;

    private Long receiver;

    private String message;

    private Boolean deleted;

    private ReadReceipt readReceipt;

    private Map<Reaction, Integer> reactions;

    private DirectMessageMediaFileDto media;

    public static DirectMessageResponseDto fromDirectMessage(DirectMessage directMessage) {

        DirectMessageResponseDto responseDto = new DirectMessageResponseDto();
        responseDto.setId(directMessage.getId());
        responseDto.setSender(directMessage.getSender().getId());
        responseDto.setReceiver(directMessage.getReceiver().getId());
        if (!directMessage.isDeleted()) {
            responseDto.setMessage(directMessage.getMessage()); // Only set message if not deleted
        }
        responseDto.setCreatedAt(directMessage.getCreatedAt());
        responseDto.setDeleted(directMessage.isDeleted());
        responseDto.setReadReceipt(directMessage.getReadReceipt());
        return responseDto;
    }
}
