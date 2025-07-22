package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.Visibility;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecentChatDto {

    private String chatId;

    private String name;

    private String image;

    private String status;

    private Visibility visibility;

    private LastMessageDto recentMessage;
}
