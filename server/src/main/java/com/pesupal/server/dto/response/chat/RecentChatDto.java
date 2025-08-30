package com.pesupal.server.dto.response.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.Visibility;
import lombok.Data;

import java.net.URL;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecentChatDto {

    private String chatId;

    private String name;

    private URL image;

    private String status;

    private Visibility visibility;

    private LastMessageDto recentMessage;
}
