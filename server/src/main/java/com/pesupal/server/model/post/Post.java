package com.pesupal.server.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Post extends CreationTimeAuditable {

    @ManyToOne
    @JsonIgnore
    private Org org;

    @ManyToOne
    private User user;

    private String title;

    @Column(nullable = false, length = 3000)
    private String description;

    private boolean media;

    private boolean hasPoll;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status;

    private boolean commentable;

    private boolean shareable;

    private boolean bookmarkable;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PostLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PostComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PostMedia> postMedia = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PostTag> tags = new ArrayList<>();
}
