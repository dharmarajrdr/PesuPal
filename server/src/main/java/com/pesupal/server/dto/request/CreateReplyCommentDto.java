package com.pesupal.server.dto.request;

import com.pesupal.server.model.post.PostReply;
import lombok.Data;

@Data
public class CreateReplyCommentDto {

    private Long commentId;

    private String message;

    public PostReply toPostReply() {

        PostReply postReply = new PostReply();
        postReply.setMessage(this.message);
        return postReply;
    }
}
