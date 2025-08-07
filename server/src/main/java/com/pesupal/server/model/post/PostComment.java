package com.pesupal.server.model.post;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class PostComment extends CreationTimeAuditable {

    @ManyToOne
    private Post post;

    @ManyToOne
    private OrgMember commenter;

    @Column(nullable = false, length = 3000)
    private String message;

    @OneToMany(mappedBy = "postComment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostReply> replies = new ArrayList<>();
}
