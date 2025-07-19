package com.pesupal.server.repository;

import com.pesupal.server.model.post.Poll;
import com.pesupal.server.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {

    Optional<Poll> findByPost(Post post);
}
