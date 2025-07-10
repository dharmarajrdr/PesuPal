package com.pesupal.server.service.interfaces;

public interface PostLikeService {

    void likePost(Long postId, Long userId, Long orgId);

    void unlikePost(Long postId, Long userId, Long orgId);
}
