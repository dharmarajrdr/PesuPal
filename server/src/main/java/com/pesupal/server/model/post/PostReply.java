package com.pesupal.server.model.post;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class PostReply extends CreationTimeAuditable {

    @ManyToOne
    private PostComment postComment;

    @ManyToOne
    private OrgMember replier;

    @Column(nullable = false, length = 3000)
    private String message;
}
