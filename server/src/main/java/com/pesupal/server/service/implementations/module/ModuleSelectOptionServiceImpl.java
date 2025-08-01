package com.pesupal.server.service.implementations.module;

import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleSelectOption;
import com.pesupal.server.repository.ModuleSelectOptionRepository;
import com.pesupal.server.service.interfaces.module.ModuleSelectOptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ModuleSelectOptionServiceImpl implements ModuleSelectOptionService {

    private final ModuleSelectOptionRepository moduleSelectOptionRepository;

    /**
     * Saves a module select option to the database.
     *
     * @param moduleSelectOption
     * @return
     */
    @Override
    public ModuleSelectOption save(ModuleSelectOption moduleSelectOption) {

        return moduleSelectOptionRepository.save(moduleSelectOption);
    }

    /**
     * Saves a list of module select options to the database.
     *
     * @param selectOptions
     */
    @Override
    public void saveAll(List<ModuleSelectOption> selectOptions) {

        moduleSelectOptionRepository.saveAll(selectOptions);
    }

    /**
     * Deletes all module select options associated with a specific module field.
     *
     * @param moduleField
     */
    @Override
    public void deleteAllByModuleField(ModuleField moduleField) {

        moduleSelectOptionRepository.deleteAllByModuleField(moduleField);
    }

    /**
     * Retrieves all module select options associated with a specific module field.
     *
     * @param moduleField
     * @return
     */
    @Override
    public List<ModuleSelectOption> getAllByModuleField(ModuleField moduleField) {

        return moduleSelectOptionRepository.findAllByModuleField(moduleField);
    }

    /**
     * Retrieves a module select option by its associated module field and ID.
     *
     * @param field
     * @param selectOptionId
     * @return
     */
    @Override
    public ModuleSelectOption getModuleSelectOptionByModuleFieldAndId(ModuleField field, Long selectOptionId) {

        return moduleSelectOptionRepository.findByModuleFieldAndId(field, selectOptionId).orElseThrow(() -> new DataNotFoundException("Select option with ID '" + selectOptionId + "' not found for field: " + field.getName()));
    }
}
