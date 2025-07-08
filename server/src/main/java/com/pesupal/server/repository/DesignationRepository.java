package com.pesupal.server.repository;

import com.pesupal.server.model.user.Designation;
import com.pesupal.server.projections.DesignationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {

    List<DesignationProjection> findAllByOrgId(Long orgId);
}
