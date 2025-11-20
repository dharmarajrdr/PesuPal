package com.pesupal.server.repository.post;

import com.pesupal.server.model.post.Bookmark;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.user.OrgMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    boolean existsBookmarkByOrgMemberAndPost(OrgMember orgMember, Post post);

    Optional<Bookmark> findBookmarkByOrgMemberAndPost(OrgMember orgMember, Post post);
    
    Page<Bookmark> findAllByOrgMemberOrderByCreatedAtDesc(OrgMember orgMember, Pageable pageable);
}
