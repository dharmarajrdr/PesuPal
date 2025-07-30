package com.pesupal.server.repository;

import com.pesupal.server.model.department.Department;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    Optional<OrgMember> findByPublicId(String publicId);

    @Query("""
                SELECT o FROM OrgMember o
                WHERE o.org.id = :orgId
                  AND (LOWER(o.displayName) LIKE LOWER(CONCAT('%', :search, '%'))
                       OR LOWER(o.user.email) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<OrgMember> searchOrgMembers(@Param("orgId") Long orgId, @Param("search") String search, Pageable pageable);

    @Query(value = """
            SELECT o.*
            FROM org_member o
                JOIN users u ON o.user_id = u.id
            WHERE o.org_id = :orgId
                AND (o.display_name % :search OR u.email % :search)
            ORDER BY GREATEST(
                similarity(o.display_name, :search),
                similarity(u.email, :search)
            ) DESC
            """,
            nativeQuery = true)
    Page<OrgMember> fuzzySearchOrgMembers(@Param("orgId") Long orgId, @Param("search") String search, Pageable pageable);

}
