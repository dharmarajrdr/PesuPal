package com.pesupal.server.projections;

import java.time.LocalDateTime;

public interface RecentGroupChatProjection {

    String getGroupId();

    String getGroupName();

    String getGroupVisibility();

    String getSenderDisplayPicture();

    String getSenderName();

    String getContent();

    Boolean getIncludedMedia();

    LocalDateTime getCreatedAt();
}
