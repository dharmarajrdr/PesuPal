package com.pesupal.server.service.implementations.module;

import com.pesupal.server.dto.request.module.UpdateModulePermissionDto;
import com.pesupal.server.dto.response.module.ModulePermissionDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.helpers.ModuleHelper;
import com.pesupal.server.helpers.StringHelper;
import com.pesupal.server.model.module.*;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.module.ModulePermissionRepository;
import com.pesupal.server.service.interfaces.module.ModuleMemberService;
import com.pesupal.server.service.interfaces.module.ModulePermissionService;
import com.pesupal.server.service.interfaces.module.ModuleService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModulePermissionServiceImpl extends CurrentValueRetriever implements ModulePermissionService {

    private final ModuleService moduleService;
    private final ModuleMemberService moduleMemberService;
    private final ModulePermissionRepository modulePermissionRepository;

    public ModulePermissionServiceImpl(@Lazy ModuleMemberService moduleMemberService, ModulePermissionRepository modulePermissionRepository, ModuleService moduleService) {
        this.moduleService = moduleService;
        this.moduleMemberService = moduleMemberService;
        this.modulePermissionRepository = modulePermissionRepository;
    }

    /**
     * Retrieves a module permission by module ID and role.
     *
     * @param module
     * @param moduleRole
     * @return
     */
    @Override
    public ModulePermission getModulePermissionByModuleAndRole(Module module, ModuleRole moduleRole) {

        return modulePermissionRepository.findByModuleAndRole(module, moduleRole).orElseThrow(() -> new DataNotFoundException("Permission configuration missing for role '" + moduleRole + "' in this module."));
    }

    /**
     * Initializes the permissions for a module based on predefined roles.
     *
     * @param module
     */
    @Override
    public void initializeModulePermissions(Module module) {

        ModulePermission ownerModulePermission = ModulePermission.builder().module(module).role(ModuleRole.OWNER).createRecord(true).readRecord(true).manageMembers(true).deleteRecord(true).build();
        modulePermissionRepository.save(ownerModulePermission);

        ModulePermission maintainerModulePermission = ModulePermission.builder().module(module).role(ModuleRole.MAINTAINER).createRecord(true).readRecord(true).manageMembers(false).deleteRecord(false).build();
        modulePermissionRepository.save(maintainerModulePermission);

        ModulePermission memberModulePermission = ModulePermission.builder().module(module).role(ModuleRole.MEMBER).createRecord(false).readRecord(true).manageMembers(false).deleteRecord(false).build();
        modulePermissionRepository.save(memberModulePermission);
    }

    /**
     * Deletes all permissions associated with a module.
     *
     * @param moduleId
     */
    @Override
    public void deleteAllPermissionsInModule(String moduleId) {

        modulePermissionRepository.deleteAllByModule_PublicId(moduleId);
    }

    /**
     * Verifies the accessibility of a module based on the current user's organization membership and the module's accessibility settings.
     *
     * @param module
     * @return
     */
    @Override
    public ModulePermission verifyModuleAccessibility(Module module, OrgMember orgMember) {

        ModuleAccessibility moduleAccessibility = module.getAccessibility();
        switch (moduleAccessibility) {
            case ONLY_ME: {
                if (!module.getCreatedBy().getPublicId().equals(orgMember.getPublicId())) {
                    throw new PermissionDeniedException("You do not have permission to access this module.");
                }
                return ModulePermission.builder().role(ModuleRole.OWNER).createRecord(true).readRecord(true).deleteRecord(true).manageMembers(true).clearRecords(true).build();
            }
            case ANYONE_IN_ORG: {
                if (!module.getCreatedBy().getOrg().getId().equals(orgMember.getOrg().getId())) {
                    throw new PermissionDeniedException("Module does not exist in your organization.");
                }
                return ModulePermission.builder().createRecord(true).readRecord(true).deleteRecord(true).manageMembers(true).clearRecords(true).build();
            }
            case SELECTIVE_MEMBERS: {
                ModuleMember moduleMember = moduleMemberService.getModuleMemberByOrgMemberAndModule(orgMember, module);
                if (!moduleMember.isActive()) {
                    throw new PermissionDeniedException("You are no longer part of this module.");
                }
                return getModulePermissionByModuleAndRole(module, moduleMember.getRole());
            }
            default: {
                throw new PermissionDeniedException("Module accessibility is not defined.");
            }
        }
    }

    /**
     * Retrieves the permissions for a module.
     *
     * @param moduleId
     * @return
     */
    @Override
    public List<ModulePermissionDto> getModulePermissions(String moduleId) {

        OrgMember orgMember = getCurrentOrgMember();
        Module module = moduleService.getModuleById(moduleId);
        if (!ModuleHelper.isModuleOwner(module, orgMember)) {
            throw new PermissionDeniedException("You do not have permission to access this module.");
        }

        List<ModulePermission> modulePermissions = modulePermissionRepository.findAllByModule(module);
        Map<String, ModulePermissionDto> permissions = new HashMap<>();
        for (ModulePermission modulePermission : modulePermissions) {
            ModuleRole role = modulePermission.getRole();
            for (Field field : ModulePermission.class.getDeclaredFields()) {
                if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
                    field.setAccessible(true); // allow access to private fields
                    try {
                        String fieldName = StringHelper.camelCaseToTitle(field.getName());
                        boolean fieldValue = field.getBoolean(modulePermission);
                        ModulePermissionDto modulePermissionDto = permissions.computeIfAbsent(fieldName, k -> new ModulePermissionDto(fieldName));
                        switch (role) {
                            case OWNER -> modulePermissionDto.setOwner(fieldValue);
                            case MAINTAINER -> modulePermissionDto.setMaintainer(fieldValue);
                            case MEMBER -> modulePermissionDto.setMember(fieldValue);
                        }
                    } catch (IllegalAccessException ignored) {
                    }
                }
            }
        }

        List<ModulePermissionDto> permissionDtos = new ArrayList<>();
        for (Map.Entry<String, ModulePermissionDto> entry : permissions.entrySet()) {
            permissionDtos.add(entry.getValue());
        }
        return permissionDtos;
    }

    /**
     * Updates the permissions for a module.
     *
     * @param updateModulePermissionDto
     */
    @Override
    public void updateModulePermissions(UpdateModulePermissionDto updateModulePermissionDto) throws IllegalAccessException {

        OrgMember orgMember = getCurrentOrgMember();
        Module module = moduleService.getModuleById(updateModulePermissionDto.getModuleId());

        if (!ModuleHelper.isModuleOwner(module, orgMember)) {
            throw new PermissionDeniedException("You do not have permission to update permissions in this module.");
        }

        String fieldName = StringHelper.toCamelCase(updateModulePermissionDto.getName());
        boolean isEnabled = updateModulePermissionDto.isEnable();
        ModuleRole role = updateModulePermissionDto.getRole();

        ModulePermission modulePermission = getModulePermissionByModuleAndRole(module, role);

        for (Field field : ModulePermission.class.getDeclaredFields()) {
            if (field.getName().equals(fieldName) && (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class))) {
                field.setAccessible(true); // allow access to private fields
                field.setBoolean(modulePermission, isEnabled);
                modulePermissionRepository.save(modulePermission);
                return;
            }
        }

        throw new DataNotFoundException("Field '" + fieldName + "' does not exist.");
    }

}
