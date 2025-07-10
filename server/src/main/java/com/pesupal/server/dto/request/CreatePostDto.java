package com.pesupal.server.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.post.Post;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePostDto {

    private String title;

    private String description;

    private boolean media = false;

    private boolean commentable = true;

    private boolean shareable = true;

    private boolean bookmarkable = true;

    private Set<UUID> mediaIds = new HashSet<>();

    public Post toPost() {

        Post post = new Post();
        post.setTitle(this.title);
        post.setDescription(this.description);
        post.setMedia(this.media);
        post.setCommentable(this.commentable);
        post.setShareable(this.shareable);
        post.setBookmarkable(this.bookmarkable);
        return post;
    }
}
