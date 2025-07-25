package com.pesupal.server.repository;

import com.pesupal.server.model.department.Department;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrgMemberRepository extends JpaRepository<OrgMember, Long> {

    Optional<OrgMember> findByUserAndOrg(User user, Org org);

    Integer countByOrg(Org org);

    Boolean existsByUserAndOrg(User user, Org org);

    List<OrgMember> findByUser(User user);

    Boolean existsByUserIdAndOrgId(Long userId, Long orgId);

    List<OrgMember> findAllByOrgIdOrderByDisplayNameAsc(Long orgId);

    List<OrgMember> findAllByOrgAndDepartmentOrderByDisplayName(Org org, Department department);

    Optional<OrgMember> findByUser_PublicIdAndOrg_PublicId(String publicUserId, String publicOrgId);
}
