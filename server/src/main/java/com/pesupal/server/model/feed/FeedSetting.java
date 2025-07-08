package com.pesupal.server.model.feed;

import jakarta.persistence.Embeddable;

@Embeddable
public class FeedSetting {

    private Boolean commentable;

    private Boolean shareable;

    private Boolean bookmarkable;
}
