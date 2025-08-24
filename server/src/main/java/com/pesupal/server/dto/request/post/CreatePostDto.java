package com.pesupal.server.dto.request.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.dto.response.MediaDto;
import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.model.post.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePostDto {

    private String title;

    private String description;

    private Boolean commentable = true;

    private Boolean shareable = true;

    private Boolean bookmarkable = true;

    private Set<MediaDto> mediaIds = new HashSet<>();

    private Set<String> tags = new HashSet<>();

    private Boolean media = false;

    private LocalDateTime scheduledAt;

    private CreatePollDto poll;

    private PostStatus status = PostStatus.PUBLISHED;

    public Post toPost() {

        Post post = new Post();
        post.setTitle(this.title);
        post.setDescription(this.description);
        post.setMedia(!this.mediaIds.isEmpty());
        post.setCommentable(this.commentable);
        post.setShareable(this.shareable);
        post.setBookmarkable(this.bookmarkable);
        post.setCreatedAt(this.scheduledAt);
        post.setStatus(this.status);
        return post;
    }

    public void applyToPost(Post post) {
        if (this.title != null) {
            post.setTitle(this.title);
        }
        if (this.description != null) {
            post.setDescription(this.description);
        }
        if (this.commentable != null) {
            post.setCommentable(this.commentable);
        }
        if (this.shareable != null) {
            post.setShareable(this.shareable);
        }
        if (this.bookmarkable != null) {
            post.setBookmarkable(this.bookmarkable);
        }
        if (this.media != null) {
            post.setMedia(this.media);
        }
        if (this.scheduledAt != null) {
            post.setCreatedAt(this.scheduledAt);
        }
        if (this.status != null) {
            post.setStatus(this.status);
        }
    }
}
