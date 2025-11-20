package com.pesupal.server.service.interfaces.post;

import com.pesupal.server.dto.response.post.PostLikesDto;
import com.pesupal.server.dto.response.post.PostsListDto;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PostLikeService {

    void likePost(String postId);

    void unlikePost(String postId);

    List<PostLikesDto> getPostLikes(String postId);

    PostsListDto getAllLikedPosts(int page, int size, Sort sort);
}
