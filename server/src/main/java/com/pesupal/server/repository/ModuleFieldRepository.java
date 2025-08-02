package com.pesupal.server.repository;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleFieldRepository extends JpaRepository<ModuleField, Long> {

    List<ModuleField> findAllByModuleOrderById(Module module);

    boolean existsByNameAndModule(String fieldName, Module module);

    void deleteAllByModule_PublicId(String moduleId);
}
