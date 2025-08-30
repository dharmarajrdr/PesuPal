package com.pesupal.server.repository.post;

import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.user.OrgMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndOrgId(Long id, Long orgId);

    Page<Post> findAllByOrgIdAndCreator_PublicIdAndStatus(Long orgId, String creatorId, Pageable pageable, PostStatus postStatus);

    Optional<Post> findByPublicId(String publicId);

    Optional<Post> findByPublicIdAndOrgId(String postId, Long orgId);

    Page<Post> findAllByCreatorAndStatusAndCreatedAtAfter(OrgMember orgMember, PostStatus postStatus, LocalDateTime now, Pageable pageable);

    @Query("""
            SELECT p FROM Post p
            WHERE p.org = :org
                  AND p.status = 'PUBLISHED'
                  AND NOT EXISTS (
                      SELECT 1 FROM p.likes l WHERE l.liker = :orgMember
                  )
            """)
    Page<Post> getUnlikedPostsByOrgMember(@Param("org") Org org, @Param("orgMember") OrgMember orgMember, Pageable pageable);

    @Query("""
            SELECT p FROM Post p
            LEFT JOIN p.likes l
            LEFT JOIN p.comments c
            WHERE p.org = :org AND p.status = 'PUBLISHED'
            GROUP BY p
            ORDER BY (COUNT(l) + COUNT(c)) DESC
            LIMIT :limit
            """)
    List<Post> getTrendingPostsByEngagement(Org org, int limit);

    @Query(value = """
            SELECT * FROM post
            WHERE org_id = :orgId
            AND to_tsvector('english', coalesce(title,'') || ' ' || coalesce(description,'')) 
            @@ to_tsquery(:query)
            ORDER BY created_at DESC
            """,
            countQuery = """
                    SELECT count(*) FROM post
                    WHERE org_id = :orgId
                    AND to_tsvector('english', coalesce(title,'') || ' ' || coalesce(description,'')) 
                    @@ to_tsquery(:query)
                    """,
            nativeQuery = true)
    Slice<Post> searchPostsByOrg(@Param("orgId") Long orgId, @Param("query") String query, Pageable pageable);

}
