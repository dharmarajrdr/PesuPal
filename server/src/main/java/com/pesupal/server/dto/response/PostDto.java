package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.post.Post;
import lombok.Data;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {

    private Long id;

    private UserBasicInfoDto owner;

    private String title;

    private String description;

    private LocalDateTime createdAt;

    private List<String> tags;

    private List<URL> media;

    private PostImpressionDto impression;

    private boolean commentable;

    private boolean shareable;

    private boolean bookmarkable;

    private boolean liked;

    private boolean bookmarked;

    private boolean isCreator;

    private PollDto poll;

    public static PostDto fromPost(Post post) {

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setDescription(post.getDescription());
        postDto.setCommentable(post.isCommentable());
        postDto.setBookmarkable(post.isBookmarkable());
        postDto.setShareable(post.isShareable());
        return postDto;
    }
}
