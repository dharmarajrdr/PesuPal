package com.pesupal.server.repository;

import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.model.post.PostTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    Page<PostTag> findAllByTag_NameAndPost_Org_IdAndPost_StatusOrderByPost_CreatedAtDesc(String tagName, Long orgId, PostStatus postStatus, Pageable pageable);
}
