package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.response.post.PostLikesDto;

import java.util.List;

public interface PostLikeService {

    void likePost(String postId);

    void unlikePost(String postId);

    List<PostLikesDto> getPostLikes(String postId);
}
