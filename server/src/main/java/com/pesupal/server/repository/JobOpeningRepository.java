package com.pesupal.server.repository;

import com.pesupal.server.enums.JobOpeningStatus;
import com.pesupal.server.model.recruit.JobOpening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOpeningRepository extends JpaRepository<JobOpening, Long> {

    List<JobOpening> findAllByOrgIdAndStatus(Long orgId, JobOpeningStatus status);

}
