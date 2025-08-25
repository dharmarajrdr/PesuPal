package com.pesupal.server.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.model.PublicAccessModel;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = false)
public class Post extends PublicAccessModel {

    @ManyToOne
    @JsonIgnore
    private Org org;

    @ManyToOne
    @JsonIgnore
    private OrgMember creator;

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

    private String postMentionLabel;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<PostMention> mentions = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PostLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PostComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PostMedia> postMedia = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<PostTag> tags = new ArrayList<>();
}
