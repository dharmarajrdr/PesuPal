package com.pesupal.server.repository;

import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleSelectOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleSelectOptionRepository extends JpaRepository<ModuleSelectOption, Long> {

    void deleteAllByModuleField(ModuleField moduleField);

    List<ModuleSelectOption> findAllByModuleField(ModuleField moduleField);

    Optional<ModuleSelectOption> findByModuleFieldAndValue(ModuleField field, String value);

    Optional<ModuleSelectOption> findByModuleFieldAndId(ModuleField field, Long selectOptionId);
}
