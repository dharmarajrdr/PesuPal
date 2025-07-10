package com.pesupal.server.repository;

import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.post.PostLike;
import com.pesupal.server.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPostAndLiker(Post post, User liker);

    Optional<PostLike> findByPostAndLiker(Post post, User user);

    List<PostLike> findByPostId(Long postId);
}
