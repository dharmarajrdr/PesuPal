package com.pesupal.server.repository.post;

import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.post.PostTag;
import com.pesupal.server.model.post.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    Page<PostTag> findAllByTag_NameAndPost_Org_IdAndPost_StatusOrderByPost_CreatedAtDesc(String tagName, Long orgId, PostStatus postStatus, Pageable pageable);

    @Query("""
                SELECT pt.tag FROM PostTag pt
                WHERE pt.post.org = :org
                GROUP BY pt.tag
                ORDER BY COUNT(pt.post) DESC
                LIMIT :limit
            """)
    List<Tag> findTopTagsByPostCount(Org org, int limit);
}
