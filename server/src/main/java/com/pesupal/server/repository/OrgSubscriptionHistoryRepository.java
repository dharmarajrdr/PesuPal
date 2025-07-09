package com.pesupal.server.repository;

import com.pesupal.server.model.org.OrgSubscriptionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrgSubscriptionHistoryRepository extends JpaRepository<OrgSubscriptionHistory, Long> {

    List<OrgSubscriptionHistory> findByOrgId(Long orgId);

    @Query("SELECT MAX(o.endDate) FROM OrgSubscriptionHistory o WHERE o.org.id = :orgId")
    LocalDateTime findLatestEndDateByOrgId(@Param("orgId") Long orgId);
}
