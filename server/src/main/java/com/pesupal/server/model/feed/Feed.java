package com.pesupal.server.model.feed;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Feed extends CreationTimeAuditable {

    @ManyToOne
    private Org org;

    @ManyToOne
    private User user;

    private String message;

    private FeedSetting feedSetting;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FeedMedia> mediaFiles;
}
