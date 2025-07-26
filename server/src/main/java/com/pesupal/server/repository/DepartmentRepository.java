package com.pesupal.server.repository;

import com.pesupal.server.model.department.Department;
import com.pesupal.server.model.org.Org;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findAllByOrgOrderByOrg_DisplayNameAsc(Org org);

    Optional<Department> findByIdAndOrg(Long departmentId, Org org);

    Optional<Department> findByPublicIdAndOrg(String departmentPublicId, Org org);
}
