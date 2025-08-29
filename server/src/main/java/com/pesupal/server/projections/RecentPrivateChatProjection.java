package com.pesupal.server.projections;

import com.pesupal.server.enums.MessageType;
import com.pesupal.server.model.chat.MessageStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public interface RecentPrivateChatProjection {

    UUID getDisplayPicture();

    String getDisplayName();

    String getUserStatus();

    String getSenderName();

    String getContent();

    Boolean getIncludedMedia();

    LocalDateTime getCreatedAt();

    String getReadReceipt();

    String getChatPublicId();

    MessageStatus getMessageStatus();

    MessageType getMessageType();
}
