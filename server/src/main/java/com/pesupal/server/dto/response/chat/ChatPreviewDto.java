package com.pesupal.server.dto.response.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.dto.request.chat.group_message.UpdateGroupChatConfigurationDto;
import com.pesupal.server.enums.Visibility;
import lombok.Data;

import java.net.URL;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatPreviewDto {

    private String displayName;

    private URL displayPicture;

    private String description;

    private Visibility visibility;

    private Long pinnedId;

    private String chatId;

    private String userId;

    private boolean active; // user active

    private boolean groupActive;

    private Integer participantsCount;

    private Boolean reopenable;

    private UpdateGroupChatConfigurationDto groupChatConfiguration;
}
