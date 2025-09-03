package com.pesupal.server.repository.org;

import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.org.OrgRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrgRoleRepository extends JpaRepository<OrgRole, Long> {

    List<OrgRole> findAllByCreatedBy_Org(Org createdByOrg);

    boolean existsByCreatedBy_OrgAndName(Org createdByOrg, String name);
}
