package com.pesupal.server.model.post;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.User;
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
    private User commenter;

    private String message;

    @OneToMany(mappedBy = "postComment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostReply> replies = new ArrayList<>();
}
