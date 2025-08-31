package com.pesupal.server.repository.org;

import com.pesupal.server.model.department.Department;
import com.pesupal.server.model.org.Org;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findAllByOrgOrderByOrg_DisplayNameAsc(Org org);

    Optional<Department> findByPublicId(String departmentId);

    Optional<Department> findByPublicIdAndOrg(String departmentId, Org org);
}
