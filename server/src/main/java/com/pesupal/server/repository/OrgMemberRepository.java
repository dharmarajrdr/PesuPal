package com.pesupal.server.repository;

import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrgMemberRepository extends JpaRepository<OrgMember, Long> {

    Optional<OrgMember> findByUserAndOrg(User user, Org org);
}
