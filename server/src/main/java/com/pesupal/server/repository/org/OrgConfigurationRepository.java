package com.pesupal.server.repository.org;

import com.pesupal.server.enums.OrgAction;
import com.pesupal.server.model.org.OrgConfiguration;
import com.pesupal.server.model.org.OrgRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrgConfigurationRepository extends JpaRepository<OrgConfiguration, Long> {

    boolean existsByRoleAndPermittedAction(OrgRole role, OrgAction permittedAction);

    Optional<OrgConfiguration> findByRoleAndPermittedAction(OrgRole orgRole, OrgAction orgAction);
}
