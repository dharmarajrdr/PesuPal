package com.pesupal.server.repository;

import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleSelectOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleSelectOptionRepository extends JpaRepository<ModuleSelectOption, Long> {

    void deleteAllByModuleField(ModuleField moduleField);
}
