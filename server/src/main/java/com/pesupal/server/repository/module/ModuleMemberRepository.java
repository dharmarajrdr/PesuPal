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

    List<ModuleMember> findAllByOrgMember(OrgMember orgMember);

    void deleteAllByModule_PublicId(String moduleId);

    @Query("""
                    SELECT om
                    FROM OrgMember om
                    WHERE (
                        LOWER(om.displayName) LIKE LOWER(CONCAT('%', :search, '%'))
                        OR LOWER(om.userName) LIKE LOWER(CONCAT('%', :search, '%'))
                    )
                    AND (
                            om.id NOT IN (
                                SELECT mm.orgMember.id
                                FROM ModuleMember mm
                                WHERE mm.module.publicId = :moduleId
                            ) OR om.id IN (
                                select mm.orgMember.id
                                from ModuleMember mm
                                where mm.module.publicId = :moduleId
                                AND mm.active = false
                            )
                    )
                    ORDER BY om.displayName ASC
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
                    AND mm.active = true
                    ORDER BY mm.orgMember.displayName ASC
            """)
    Page<ModuleMember> getMembersOfModule(String moduleId, String search, Pageable pageable);

    boolean existsByModule_PublicIdAndOrgMember_PublicIdAndActive(String moduleId, String userId, boolean active);

    @Query("""
            select m from Module m
            join OrgMember om on om.id = m.createdBy.id
            join Org o on o.id = om.org.id
            where m.accessibility = 'ANYONE_IN_ORG'
                  OR m.createdBy.id = 5
                  OR m.id in (select mm.module.id from ModuleMember mm where mm.orgMember.id = :id and mm.active = true)
            order by m.name, m.createdAt
            """)
    List<Module> getAllModulesUserIsPartOf(Long id);    // get public modules as well
}
