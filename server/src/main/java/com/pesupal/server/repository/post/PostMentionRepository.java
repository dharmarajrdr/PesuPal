package com.pesupal.server.repository.post;

import com.pesupal.server.model.post.PostMention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostMentionRepository extends JpaRepository<PostMention, Long> {
}
