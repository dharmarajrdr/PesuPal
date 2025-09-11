package com.pesupal.server.repository.org;

import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.org.OrgRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrgRoleRepository extends JpaRepository<OrgRole, Long> {

    boolean existsByCreatedBy_OrgAndName(Org createdByOrg, String name);

    Optional<OrgRole> findByNameAndCreatedBy_Org(String name, Org createdByOrg);

    List<OrgRole> findAllByCreatedBy_OrgOrderByName(Org org);
}
