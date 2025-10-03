package com.pesupal.server.projections;

import com.pesupal.server.enums.MessageType;
import com.pesupal.server.model.chat.MessageStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public interface RecentGroupChatProjection {

    String getGroupId();

    String getGroupName();

    String getGroupVisibility();

    UUID getSenderDisplayPicture();

    String getSenderName();

    String getContent();

    Boolean getIncludedMedia();

    LocalDateTime getCreatedAt();

    MessageStatus getMessageStatus();

    MessageType getMessageType();
}
