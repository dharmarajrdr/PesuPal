package com.pesupal.server.model.post;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class PostReply extends CreationTimeAuditable {

    @ManyToOne
    private PostComment postComment;

    @ManyToOne
    private User replier;

    private String message;
}
