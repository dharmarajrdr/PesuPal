package com.pesupal.server.service.implementations.module;

import com.pesupal.server.dto.request.module.CreateModuleDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.ModuleRepository;
import com.pesupal.server.service.interfaces.module.ModulePermissionService;
import com.pesupal.server.service.interfaces.module.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModuleServiceImpl extends CurrentValueRetriever implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModulePermissionService modulePermissionService;

    /**
     * Checks if the current user is the owner of the module.
     *
     * @param module
     * @param orgMember
     * @return
     */
    @Override
    public boolean isModuleOwner(Module module, OrgMember orgMember) {

        return module.getCreatedBy().getId().equals(orgMember.getId());
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
}
