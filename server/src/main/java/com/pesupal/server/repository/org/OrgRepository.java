package com.pesupal.server.repository.org;

import com.pesupal.server.model.org.Org;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrgRepository extends JpaRepository<Org, Long> {

    boolean existsByUniqueName(String uniqueName);

    Optional<Org> findByPublicId(String orgId);

    List<Org> findAllByActive(boolean active);
}
