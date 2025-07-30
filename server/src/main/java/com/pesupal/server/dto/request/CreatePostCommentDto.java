package com.pesupal.server.dto.request;

import com.pesupal.server.model.post.PostComment;
import lombok.Data;

@Data
public class CreatePostCommentDto {

    private String postId;

    private String message;

    public PostComment toPostComment() {

        PostComment postComment = new PostComment();
        postComment.setMessage(this.message);
        return postComment;
    }
}
