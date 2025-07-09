package com.pesupal.server.dto.response;

import lombok.Data;

@Data
public class RecentChatDto {

    private String name;

    private String image;

    private String status;

    private LastMessageDto recentMessage;
}
