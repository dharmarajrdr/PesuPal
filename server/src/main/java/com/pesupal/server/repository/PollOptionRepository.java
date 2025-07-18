package com.pesupal.server.repository;

import com.pesupal.server.model.post.PollOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollOptionRepository extends JpaRepository<PollOption, Long> {
}
