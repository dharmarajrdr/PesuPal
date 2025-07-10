package com.pesupal.server.model.post;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class PostComment extends CreationTimeAuditable {

    @ManyToOne
    private Post post;

    @ManyToOne
    private User commenter;

    private String message;
}
