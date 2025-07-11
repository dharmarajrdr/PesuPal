package com.pesupal.server.repository;

import com.pesupal.server.model.recruit.CandidateTimeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateTimelineRepository extends JpaRepository<CandidateTimeline, Long> {
}
