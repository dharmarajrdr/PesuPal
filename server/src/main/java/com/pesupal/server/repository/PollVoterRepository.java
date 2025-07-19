package com.pesupal.server.repository;

import com.pesupal.server.model.post.PollVoter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PollVoterRepository extends JpaRepository<PollVoter, Long> {

    Optional<PollVoter> findByPollOption_PollIdAndVoter_Id(Long pollId, Long voterId);
}
