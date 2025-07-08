package com.pesupal.server.dto.response;

import com.pesupal.server.enums.ReadReceipt;
import com.pesupal.server.model.chat.DirectMessage;
import com.pesupal.server.model.chat.DirectMessageMediaFile;
import lombok.Data;

import java.util.List;

@Data
public class DirectMessageResponseDto {

    private Long id;

    private Long sender;

    private Long receiver;

    private String message;

    private Boolean deleted;

    private ReadReceipt readReceipt;

    private List<DirectMessageMediaFile> directMessageMediaFiles;

    public static DirectMessageResponseDto fromDirectMessage(DirectMessage directMessage) {

        DirectMessageResponseDto responseDto = new DirectMessageResponseDto();
        responseDto.setId(directMessage.getId());
        responseDto.setSender(directMessage.getSender().getId());
        responseDto.setReceiver(directMessage.getReceiver().getId());
        if (!directMessage.isDeleted()) {
            responseDto.setMessage(directMessage.getMessage()); // Only set message if not deleted
        }
        responseDto.setDeleted(directMessage.isDeleted());
        responseDto.setReadReceipt(directMessage.getReadReceipt());
        responseDto.setDirectMessageMediaFiles(directMessage.getDirectMessageMediaFiles());
        return responseDto;
    }
}
