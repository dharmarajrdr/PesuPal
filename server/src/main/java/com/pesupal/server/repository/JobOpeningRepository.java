package com.pesupal.server.repository;

import com.pesupal.server.enums.JobOpeningStatus;
import com.pesupal.server.model.recruit.JobOpening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOpeningRepository extends JpaRepository<JobOpening, Long> {


    @Query("""
                SELECT j FROM JobOpening j
                WHERE j.org.id = :orgId
                AND (:status IS NULL OR j.status = :status)
                ORDER BY j.createdAt DESC
            """)
    List<JobOpening> findAllByOrgIdAndStatusOrderByCreatedAtDesc(@Param("orgId") Long orgId,
                                                                 @Param("status") JobOpeningStatus status);


}
