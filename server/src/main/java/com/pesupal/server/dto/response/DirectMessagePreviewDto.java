package com.pesupal.server.dto.response;

import lombok.Data;

@Data
public class DirectMessagePreviewDto {

    private UserPreviewDto currentUser;

    private UserPreviewDto otherUser;
}
