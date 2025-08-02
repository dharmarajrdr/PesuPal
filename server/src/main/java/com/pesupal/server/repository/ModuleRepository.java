package com.pesupal.server.repository;

import com.pesupal.server.model.module.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    Optional<Module> findByPublicId(String moduleId);

    List<Module> findAllByCreatedBy_PublicIdOrderByCreatedAtDesc(String publicId);
}
