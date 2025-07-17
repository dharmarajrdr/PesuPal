package com.pesupal.server.repository;

import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.model.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndOrgId(Long id, Long orgId);

    Page<Post> findAllByOrgIdAndUserIdAndStatus(Long orgId, Long postOwnerId, Pageable pageable, PostStatus postStatus);

    boolean existsByIdAndOrgId(Long postId, Long orgId);

    int countAllByUserIdAndOrgId(Long userId, Long orgId);
}
