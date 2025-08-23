package com.pesupal.server.repository.module;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleMember;
import com.pesupal.server.model.user.OrgMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleMemberRepository extends JpaRepository<ModuleMember, Long> {

    Optional<ModuleMember> findByOrgMemberAndModule(OrgMember orgMember, Module module);

    int countAllByModule(Module module);

    boolean existsByModule_PublicIdAndOrgMember_PublicId(String moduleId, String userId);

    List<ModuleMember> findAllByOrgMember(OrgMember orgMember);

    void deleteAllByModule_PublicId(String moduleId);

    @Query("""
                    SELECT u
                    FROM OrgMember u
                    WHERE (
                        LOWER(u.displayName) LIKE LOWER(CONCAT('%', :search, '%'))
                        OR LOWER(u.userName) LIKE LOWER(CONCAT('%', :search, '%'))
                    )
                    AND u.id NOT IN (
                        SELECT mm.orgMember.id
                        FROM ModuleMember mm
                        WHERE mm.module.publicId = :moduleId
                    )
                    ORDER BY u.displayName ASC
            """)
    Page<OrgMember> getNonMembersOfModule(String moduleId, String search, Pageable pageable);

    @Query("""
                    SELECT mm
                    FROM ModuleMember mm
                    WHERE (
                        LOWER(mm.orgMember.displayName) LIKE LOWER(CONCAT('%', :search, '%'))
                        OR LOWER(mm.orgMember.userName) LIKE LOWER(CONCAT('%', :search, '%'))
                    )
                    AND mm.module.publicId = :moduleId
                    AND mm.role IN ('MAINTAINER', 'MEMBER')
                    ORDER BY mm.orgMember.displayName ASC
            """)
    Page<ModuleMember> getMembersOfModule(String moduleId, String search, Pageable pageable);
}
