package com.pesupal.server.repository;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleMember;
import com.pesupal.server.model.user.OrgMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModuleMemberRepository extends JpaRepository<ModuleMember, Long> {

    Optional<ModuleMember> findByOrgMemberAndModule(OrgMember orgMember, Module module);
}
