package com.pesupal.server.projections;

import java.time.LocalDateTime;

public interface RecentGroupChatProjection {

    Long getGroupId();

    String getGroupName();

    String getGroupVisibility();

    String getSenderDisplayPicture();

    String getSenderName();

    String getContent();

    Boolean getIncludedMedia();

    LocalDateTime getCreatedAt();
}
