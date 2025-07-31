package com.pesupal.server.service.interfaces.module;

import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleSelectOption;

import java.util.List;

public interface ModuleSelectOptionService {

    ModuleSelectOption save(ModuleSelectOption moduleSelectOption);

    void saveAll(List<ModuleSelectOption> selectOptions);

    void deleteAllByModuleField(ModuleField moduleField);

    List<ModuleSelectOption> getAllByModuleField(ModuleField moduleField);
}
