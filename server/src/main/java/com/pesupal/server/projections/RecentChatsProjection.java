package com.pesupal.server.projections;

import java.time.LocalDateTime;

public interface RecentChatsProjection {

    String getDisplayPicture();

    String getDisplayName();

    String getUserStatus();

    String getSenderName();

    String getContent();

    Boolean getIncludedMedia();

    LocalDateTime getCreatedAt();

    String getReadReceipt();

    String getChatPublicId();
}
