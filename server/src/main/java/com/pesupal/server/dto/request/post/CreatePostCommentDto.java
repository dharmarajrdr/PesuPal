package com.pesupal.server.dto.request.post;

import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.model.post.PostComment;
import lombok.Data;

@Data
public class CreatePostCommentDto {

    private String postId;

    private String message;

    private Boolean anonymous;

    public PostComment toPostComment() {

        PostComment postComment = new PostComment();
        postComment.setMessage(this.message);
        postComment.setAnonymous(this.anonymous);
        return postComment;
    }

    public void applyToPostComment(PostComment postComment) {

        if (message != null) {
            if (message.trim().isEmpty()) {
                throw new ActionProhibitedException("Comment cannot be empty");
            }
            postComment.setMessage(message);
        }
    }
}
