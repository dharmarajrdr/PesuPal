package com.pesupal.server.repository.module;

import com.pesupal.server.enums.FieldType;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleFieldRepository extends JpaRepository<ModuleField, Long> {

    List<ModuleField> findAllByModuleOrderById(Module module);

    boolean existsByNameAndModule(String fieldName, Module module);

    void deleteAllByModule_PublicId(String moduleId);

    int countModuleFieldsByModule(Module module);

    Optional<ModuleField> findByModuleAndFieldType(Module module, FieldType fieldType);
}
