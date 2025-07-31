package com.pesupal.server.service.implementations.module;

import com.pesupal.server.dto.request.module.AddModuleFieldDto;
import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.dto.response.module.ModuleSelectOptionDto;
import com.pesupal.server.enums.FieldType;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.DuplicateDataReceivedException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleSelectOption;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.ModuleFieldRepository;
import com.pesupal.server.service.interfaces.module.ModuleFieldService;
import com.pesupal.server.service.interfaces.module.ModuleSelectOptionService;
import com.pesupal.server.service.interfaces.module.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ModuleFieldServiceImpl extends CurrentValueRetriever implements ModuleFieldService {

    private final ModuleService moduleService;
    private final ModuleFieldRepository moduleFieldRepository;
    private final ModuleSelectOptionService moduleSelectOptionService;

    /**
     * Adds a new field into a module.
     *
     * @param addModuleFieldDto
     * @return
     */
    @Override
    @Transactional
    public ModuleFieldDto addModuleField(AddModuleFieldDto addModuleFieldDto) {

        OrgMember orgMember = getCurrentOrgMember();
        Module module = moduleService.getModuleById(addModuleFieldDto.getModuleId());
        if (!moduleService.isModuleOwner(module, orgMember)) {
            throw new PermissionDeniedException("You do not have permission to add a field to this module.");
        }

        String fieldName = addModuleFieldDto.getFieldName();
        if (moduleFieldRepository.existsByName(fieldName)) {
            throw new DuplicateDataReceivedException("A field with name '" + fieldName + "' already exists in the module.");
        }

        ModuleField moduleField = addModuleFieldDto.toModuleField(module);
        moduleFieldRepository.save(moduleField);

        if (moduleField.getFieldType().equals(FieldType.SELECT)) {
            List<ModuleSelectOption> selectOptions = addModuleFieldDto.getOptions().stream().map(addModuleSelectOptionDto -> addModuleSelectOptionDto.toModuleSelectOption(moduleField)).toList();
            moduleSelectOptionService.saveAll(selectOptions);
            List<ModuleSelectOptionDto> moduleSelectOptionDtos = selectOptions.stream().map(ModuleSelectOptionDto::fromModuleSelectOption).toList();
            return ModuleFieldDto.fromModuleFieldWithOptions(moduleField, moduleSelectOptionDtos);
        }

        return ModuleFieldDto.fromModuleField(moduleField);
    }

    /**
     * Retrieves all fields for a specific module.
     *
     * @param moduleId
     * @return
     */
    @Override
    public List<ModuleFieldDto> getModuleFields(String moduleId) {

        OrgMember orgMember = getCurrentOrgMember();
        Module module = moduleService.getModuleById(moduleId);
        if (!moduleService.isModuleOwner(module, orgMember)) {
            throw new PermissionDeniedException("You do not have permission to view fields for this module.");
        }

        List<ModuleField> moduleFields = moduleFieldRepository.findAllByModuleOrderById(module);

        return moduleFields.stream().map(moduleField -> {
            FieldType fieldType = moduleField.getFieldType();
            if (fieldType.equals(FieldType.SELECT)) {
                List<ModuleSelectOption> options = moduleSelectOptionService.getAllByModuleField(moduleField);
                List<ModuleSelectOptionDto> optionDtos = options.stream().map(ModuleSelectOptionDto::fromModuleSelectOption).toList();
                return ModuleFieldDto.fromModuleFieldWithOptions(moduleField, optionDtos);
            }
            return ModuleFieldDto.fromModuleField(moduleField);
        }).toList();
    }

    /**
     * Deletes a field from a module.
     *
     * @param fieldId
     */
    @Override
    @Transactional
    public void deleteModuleField(Long fieldId) {

        OrgMember orgMember = getCurrentOrgMember();
        ModuleField moduleField = moduleFieldRepository.findById(fieldId).orElseThrow(() -> new DataNotFoundException("Module field with ID " + fieldId + " not found."));

        Module module = moduleField.getModule();
        if (!moduleService.isModuleOwner(module, orgMember)) {
            throw new PermissionDeniedException("You do not have permission to delete this field.");
        }

        FieldType fieldType = moduleField.getFieldType();
        if (fieldType.equals(FieldType.SELECT)) {
            moduleSelectOptionService.deleteAllByModuleField(moduleField);
        }

        moduleFieldRepository.delete(moduleField);
    }
}
