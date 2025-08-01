package com.pesupal.server.service.implementations.module;

import com.pesupal.server.dto.request.module.CreateModuleDto;
import com.pesupal.server.dto.response.module.ModulePreviewDto;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.helpers.ModuleHelper;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.ModuleRepository;
import com.pesupal.server.service.interfaces.module.ModuleMemberService;
import com.pesupal.server.service.interfaces.module.ModulePermissionService;
import com.pesupal.server.service.interfaces.module.ModuleService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleServiceImpl extends CurrentValueRetriever implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModuleMemberService moduleMemberService;
    private final ModulePermissionService modulePermissionService;

    public ModuleServiceImpl(ModuleRepository moduleRepository, @Lazy ModuleMemberService moduleMemberService, ModulePermissionService modulePermissionService) {
        this.moduleRepository = moduleRepository;
        this.moduleMemberService = moduleMemberService;
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
        return moduleMemberService.getAllModulesOfOrgMember(orgMember).stream().map(ModulePreviewDto::fromModule).toList();
    }
}
