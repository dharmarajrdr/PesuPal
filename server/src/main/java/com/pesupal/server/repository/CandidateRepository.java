package com.pesupal.server.repository;

import com.pesupal.server.model.recruit.Candidate;
import com.pesupal.server.model.recruit.JobOpening;
import com.pesupal.server.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    boolean existsByApplicantAndJobOpening(User applicant, JobOpening jobOpening);
}
