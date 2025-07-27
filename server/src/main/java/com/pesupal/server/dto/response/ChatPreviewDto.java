package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatPreviewDto {

    private String displayName;

    private String displayPicture;

    private Long pinnedId;

    private String chatId;

    private String userId;

    private boolean active; // user active

    private boolean groupActive;

    private Integer participantsCount;
}
