package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.response.PostLikesDto;

import java.util.List;

public interface PostLikeService {

    void likePost(Long postId, Long userId, Long orgId);

    void unlikePost(Long postId, Long userId, Long orgId);

    List<PostLikesDto> getPostLikes(Long postId, Long userId, Long orgId);
}
