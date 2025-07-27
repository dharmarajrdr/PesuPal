package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.response.PostLikesDto;

import java.util.List;

public interface PostLikeService {

    void likePost(Long postId);

    void unlikePost(Long postId);

    List<PostLikesDto> getPostLikes(Long postId);
}
