package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.post.Post;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {

    private Long id;

    private UserBasicInfoDto owner;

    private String title;

    private String description;

    private List<String> tags;

    private List<UUID> media;

    private PostImpressionDto impression;

    private boolean commentable;

    private boolean shareable;

    private boolean bookmarkable;

    public static PostDto fromPost(Post post) {

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setCommentable(post.isCommentable());
        postDto.setBookmarkable(post.isBookmarkable());
        postDto.setShareable(post.isShareable());
        return postDto;
    }
}
