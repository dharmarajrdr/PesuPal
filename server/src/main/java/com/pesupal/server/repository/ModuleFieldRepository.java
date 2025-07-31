package com.pesupal.server.repository;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleFieldRepository extends JpaRepository<ModuleField, Long> {

    boolean existsByName(String fieldName);

    List<ModuleField> findAllByModuleOrderById(Module module);
}
