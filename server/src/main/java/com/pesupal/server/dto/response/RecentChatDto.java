package com.pesupal.server.dto.response;

import lombok.Data;

@Data
public class RecentChatDto {

    private String userName;

    private String displayPicture;

    private String userStatus;

    private LastMessageDto lastMessage;
}
