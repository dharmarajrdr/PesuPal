package com.pesupal.server.service.implementations.module;

import com.pesupal.server.dto.request.module.AddModuleFieldDto;
import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.dto.response.module.ModuleSelectOptionDto;
import com.pesupal.server.dto.response.module.SystemFieldDto;
import com.pesupal.server.enums.FieldType;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.DuplicateDataReceivedException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.helpers.ModuleHelper;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.ModuleSelectOption;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.ModuleFieldRepository;
import com.pesupal.server.repository.ModuleSelectOptionRepository;
import com.pesupal.server.service.interfaces.module.ModuleFieldService;
import com.pesupal.server.service.interfaces.module.ModuleSelectOptionService;
import com.pesupal.server.service.interfaces.module.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ModuleFieldServiceImpl extends CurrentValueRetriever implements ModuleFieldService {

    private final ModuleService moduleService;
    private final ModuleFieldRepository moduleFieldRepository;
    private final ModuleSelectOptionService moduleSelectOptionService;
    private final ModuleSelectOptionRepository moduleSelectOptionRepository;

    private static final List<SystemFieldDto> SYSTEM_FIELDS = List.of(
            SystemFieldDto.builder().name("Subject").fieldType(FieldType.STRING).required(true).showInList(true).showInDetail(true).sortable(true).filterable(true).editable(true).build(),
            SystemFieldDto.builder().name("Created By").fieldType(FieldType.USER).required(true).showInList(true).showInDetail(true).sortable(false).filterable(true).build(),
            SystemFieldDto.builder().name("Created At").fieldType(FieldType.DATE_TIME).required(true).showInList(true).showInDetail(true).sortable(true).filterable(true).build(),
            SystemFieldDto.builder().name("Updated By").fieldType(FieldType.USER).showInDetail(true).sortable(true).filterable(true).build(),
            SystemFieldDto.builder().name("Updated At").fieldType(FieldType.USER).showInDetail(true).sortable(true).filterable(true).build(),
            SystemFieldDto.builder().name("Notes").fieldType(FieldType.TEXT).build()
    );

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
        if (!ModuleHelper.isModuleOwner(module, orgMember)) {
            throw new PermissionDeniedException("You do not have permission to add a field to this module.");
        }

        String fieldName = addModuleFieldDto.getFieldName();
        if (moduleFieldRepository.existsByNameAndModule(fieldName, module)) {
            throw new DuplicateDataReceivedException("A field with name '" + fieldName + "' already exists in the module.");
        }

        ModuleField moduleField = addModuleFieldDto.toModuleField(module);
        moduleFieldRepository.save(moduleField);

        if (moduleField.getFieldType().equals(FieldType.SELECT)) {
            List<ModuleSelectOption> selectOptions = addModuleFieldDto.getOptions().stream().map(addModuleSelectOptionDto -> addModuleSelectOptionDto.toModuleSelectOption(moduleField)).toList();
            moduleSelectOptionService.saveAll(selectOptions);
            List<ModuleSelectOptionDto> moduleSelectOptionDtos = selectOptions.stream().map(ModuleSelectOptionDto::fromModuleSelectOption).toList();
            return ModuleFieldDto.fromModuleFieldWithData(moduleField, moduleSelectOptionDtos);
        }

        return ModuleFieldDto.fromModuleField(moduleField);
    }

    /**
     * Retrieves all fields for a specific module by its ID.
     *
     * @param moduleId
     * @return
     */
    @Override
    public List<ModuleField> getModuleFieldsByModuleId(String moduleId) {

        Module module = moduleService.getModuleById(moduleId);
        return moduleFieldRepository.findAllByModuleOrderById(module);
    }

    /**
     * Deletes all fields associated with a specific module.
     *
     * @param moduleId
     */
    @Override
    public void deleteAllFields(String moduleId) {

        moduleSelectOptionRepository.deleteAllByModuleField_Module_PublicId(moduleId);
        moduleFieldRepository.deleteAllByModule_PublicId(moduleId);
    }

    /**
     * Adds the system fields into the module while creation.
     *
     * @param module
     */
    @Override
    public void addSystemFieldsIntoModule(Module module) {

        if (moduleFieldRepository.countModuleFieldsByModule(module) > 0) {
            throw new ActionProhibitedException("Unable to add system fields. This module already has some fields.");
        }

        moduleFieldRepository.saveAll(SYSTEM_FIELDS.stream().map(systemField -> systemField.toModuleField(module)).toList());
    }

    /**
     * Saves the system fields data.
     */
    @Override
    public Optional<Object> getSystemValueIfApplicable(ModuleRecord moduleRecord, ModuleField moduleField, Object value) {

        String fieldName = moduleField.getName();

        switch (fieldName) {
            case "Subject": {
                return Optional.of(value);
            }
            case "Created At": {
                return Optional.of(LocalDateTime.now());
            }
            case "Created By": {
                return Optional.of(getCurrentOrgMember().getPublicId());
            }
        }
        return Optional.empty();
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
        if (!ModuleHelper.isModuleOwner(module, orgMember)) {
            throw new PermissionDeniedException("You do not have permission to view fields for this module.");
        }

        List<ModuleField> moduleFields = moduleFieldRepository.findAllByModuleOrderById(module);

        return moduleFields.stream().map(moduleField -> {
            FieldType fieldType = moduleField.getFieldType();
            if (fieldType.equals(FieldType.SELECT)) {
                List<ModuleSelectOption> options = moduleSelectOptionService.getAllByModuleField(moduleField);
                List<ModuleSelectOptionDto> optionDtos = options.stream().map(ModuleSelectOptionDto::fromModuleSelectOption).toList();
                return ModuleFieldDto.fromModuleFieldWithData(moduleField, optionDtos);
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
        if (!ModuleHelper.isModuleOwner(module, orgMember)) {
            throw new PermissionDeniedException("You do not have permission to delete this field.");
        }

        FieldType fieldType = moduleField.getFieldType();
        if (fieldType.equals(FieldType.SELECT)) {
            moduleSelectOptionService.deleteAllByModuleField(moduleField);
        }

        moduleFieldRepository.delete(moduleField);
    }

}
