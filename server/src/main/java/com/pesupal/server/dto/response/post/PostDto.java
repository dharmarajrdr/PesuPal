package com.pesupal.server.dto.response.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.model.post.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {

    private String id;

    private UserBasicInfoDto owner;

    private String title;

    private String description;

    private LocalDateTime createdAt;

    private List<String> tags;

    private List<PostMediaDto> media;

    private PostImpressionDto impression;

    private boolean commentable;

    private boolean shareable;

    private boolean bookmarkable;

    private boolean liked;

    private boolean bookmarked;

    private boolean isCreator;

    private PollDto poll;

    private PostStatus status;

    private PostMentionsDto mentions;

    private boolean allowAnonymousComments;

    public static PostDto fromPost(Post post) {

        PostDto postDto = new PostDto();
        postDto.setId(post.getPublicId());
        postDto.setTitle(post.getTitle());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setDescription(post.getDescription());
        postDto.setCommentable(post.isCommentable());
        postDto.setBookmarkable(post.isBookmarkable());
        postDto.setShareable(post.isShareable());
        postDto.setStatus(post.getStatus());
        postDto.setAllowAnonymousComments(post.isAllowAnonymousComments());
        return postDto;
    }
}
