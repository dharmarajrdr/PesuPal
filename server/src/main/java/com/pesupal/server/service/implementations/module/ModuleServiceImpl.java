package com.pesupal.server.service.implementations.module;

import com.pesupal.server.dto.request.module.CreateModuleDto;
import com.pesupal.server.dto.response.module.ModuleDto;
import com.pesupal.server.dto.response.module.ModulePreviewDto;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.helpers.ModuleHelper;
import com.pesupal.server.model.module.*;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.ModuleRepository;
import com.pesupal.server.service.interfaces.module.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ModuleServiceImpl extends CurrentValueRetriever implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModuleFieldService moduleFieldService;
    private final ModuleMemberService moduleMemberService;
    private final ModuleRecordService moduleRecordService;
    private final ModulePermissionService modulePermissionService;

    public ModuleServiceImpl(ModuleRepository moduleRepository, @Lazy ModuleMemberService moduleMemberService, ModulePermissionService modulePermissionService, @Lazy ModuleRecordService moduleRecordService, @Lazy ModuleFieldService moduleFieldService) {
        this.moduleRepository = moduleRepository;
        this.moduleFieldService = moduleFieldService;
        this.moduleMemberService = moduleMemberService;
        this.moduleRecordService = moduleRecordService;
        this.modulePermissionService = modulePermissionService;
    }

    /**
     * Creates a new module.
     *
     * @param createModuleDto
     * @return
     */
    @Override
    public Module createModule(CreateModuleDto createModuleDto) {

        OrgMember orgMember = getCurrentOrgMember();
        Module module = createModuleDto.toModule();
        module.setCreatedBy(orgMember);
        module.setActive(false);
        moduleRepository.save(module);
        moduleFieldService.addSystemFieldsIntoModule(module);
        moduleMemberService.addOrgOwnerToModule(module, orgMember);
        modulePermissionService.initializeModulePermissions(module);
        return module;
    }

    /**
     * Retrieves a module by its ID.
     *
     * @param moduleId
     * @return
     */
    @Override
    public Module getModuleById(String moduleId) {

        return moduleRepository.findByPublicId(moduleId).orElseThrow(() -> new DataNotFoundException("Module with ID " + moduleId + " not found"));
    }

    /**
     * Publishes a module, making it active and available for use.
     *
     * @param moduleId
     */
    @Override
    public void publishModule(String moduleId) {

        OrgMember orgMember = getCurrentOrgMember();
        Module module = getModuleById(moduleId);

        if (!ModuleHelper.isModuleOwner(module, orgMember)) {
            throw new PermissionDeniedException("You do not have permission to publish this module.");
        }

        if (module.isActive()) {
            throw new ActionProhibitedException("This module is already published.");
        }

        module.setActive(true);
        moduleRepository.save(module);
    }

    /**
     * Retrieves a list of all module previews that current user part of.
     *
     * @return
     */
    @Override
    public List<ModulePreviewDto> getAllModulesPreview() {

        OrgMember orgMember = getCurrentOrgMember();
        return moduleMemberService.getAllModulesOfOrgMember(orgMember).stream().map(ModulePreviewDto::fromModule).filter(Objects::nonNull).toList();
    }

    /**
     * Deletes a module by its ID.
     *
     * @param moduleId
     */
    @Override
    @Transactional
    public void deleteModule(String moduleId) {

        OrgMember orgMember = getCurrentOrgMember();
        Module module = getModuleById(moduleId);

        if (!ModuleHelper.isModuleOwner(module, orgMember)) {
            throw new PermissionDeniedException("You do not have permission to delete this module.");
        }

        moduleRecordService.deleteAllRecords(moduleId);
        moduleFieldService.deleteAllFields(moduleId);
        moduleMemberService.deleteAllMembersInModule(moduleId);
        modulePermissionService.deleteAllPermissionsInModule(moduleId);
        moduleRepository.delete(module);
    }

    /**
     * Retrieves a list of module previews created by the current user.
     *
     * @return
     */
    @Override
    public List<ModulePreviewDto> getModulesCreatedByMe() {

        OrgMember orgMember = getCurrentOrgMember();
        return moduleRepository.findAllByCreatedBy_PublicIdOrderByCreatedAtDesc(orgMember.getPublicId()).stream().map(ModulePreviewDto::fromModule).filter(Objects::nonNull).toList();
    }

    /**
     * Retrieves a ModuleDto by its ID.
     *
     * @param moduleId
     * @return
     */
    @Override
    public ModuleDto getModuleDtoById(String moduleId) {

        OrgMember orgMember = getCurrentOrgMember();
        Module module = getModuleById(moduleId);

        ModuleAccessibility moduleAccessibility = module.getAccessibility();
        ModuleDto moduleDto = ModuleDto.fromModule(module);
        switch (moduleAccessibility) {
            case ANYONE_IN_ORG -> {
                if (!module.getCreatedBy().getOrg().getId().equals(orgMember.getOrg().getId())) {
                    throw new PermissionDeniedException("Module does not exist in your organization.");
                }
            }
            case ONLY_ME -> {
                if (!module.getCreatedBy().getPublicId().equals(orgMember.getPublicId())) {
                    throw new PermissionDeniedException("You do not have permission to access this module.");
                }
            }
            case SELECTIVE_MEMBERS -> {
                ModuleMember moduleMember = moduleMemberService.getModuleMemberByOrgMemberAndModule(orgMember, module);
                ModuleRole moduleRole = moduleMember.getRole();
                ModulePermission modulePermission = modulePermissionService.getModulePermissionByModuleAndRole(module, moduleRole);
                moduleDto.setCreateRecord(modulePermission.isCreateRecord());
                moduleDto.setReadRecord(modulePermission.isReadRecord());
            }
        }
        return moduleDto;
    }
}
