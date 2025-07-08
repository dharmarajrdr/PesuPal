package com.pesupal.server.repository;

import com.pesupal.server.enums.Role;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.org.OrgConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrgConfigurationRepository extends JpaRepository<OrgConfiguration, Long> {

    Optional<OrgConfiguration> findByOrgAndRole(Org org, Role role);
}
