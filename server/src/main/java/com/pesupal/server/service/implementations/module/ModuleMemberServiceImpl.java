package com.pesupal.server.service.implementations.module;

import com.pesupal.server.dto.request.module.AddModuleMemberDto;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.helpers.ModuleHelper;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleMember;
import com.pesupal.server.model.module.ModulePermission;
import com.pesupal.server.model.module.ModuleRole;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.ModuleMemberRepository;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.module.ModuleMemberService;
import com.pesupal.server.service.interfaces.module.ModulePermissionService;
import com.pesupal.server.service.interfaces.module.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModuleMemberServiceImpl extends CurrentValueRetriever implements ModuleMemberService {

    private final ModuleService moduleService;
    private final OrgMemberService orgMemberService;
    private final ModuleMemberRepository moduleMemberRepository;
    private final ModulePermissionService modulePermissionService;

    /**
     * Retrieves a ModuleMember by OrgMember and Module.
     *
     * @param orgMember
     * @param module
     * @return
     */
    @Override
    public ModuleMember getModuleMemberByOrgMemberAndModule(OrgMember orgMember, Module module) {

        return moduleMemberRepository.findByOrgMemberAndModule(orgMember, module).orElseThrow(() -> new DataNotFoundException("You are not part of this module."));
    }

    /**
     * Adds an organization owner to a module initially when the module has no members.
     *
     * @param module
     * @param orgMember
     */
    @Override
    public void addOrgOwnerToModule(Module module, OrgMember orgMember) {

        if (moduleMemberRepository.countAllByModule(module) > 0) {
            throw new ActionProhibitedException("An organization owner can only be added to a module when it has no members.");
        }
        ModuleMember moduleOwner = new ModuleMember();
        moduleOwner.setModule(module);
        moduleOwner.setOrgMember(orgMember);
        moduleOwner.setRole(ModuleRole.OWNER);
        moduleOwner.setActive(true);
        moduleMemberRepository.save(moduleOwner);
    }

    /**
     * Adds a member to a module.
     *
     * @param moduleId
     * @param addModuleMemberDto
     */
    @Override
    public void addMemberToModule(String moduleId, AddModuleMemberDto addModuleMemberDto) {

        if (addModuleMemberDto.getRole().equals(ModuleRole.OWNER)) {
            throw new ActionProhibitedException("Role 'OWNER' can only be assigned to the module creator.");
        }

        OrgMember orgMember = getCurrentOrgMember();

        Module module = moduleService.getModuleById(moduleId);

        // 1. Check if current user is the module owner
        if (!ModuleHelper.isModuleOwner(module, orgMember)) {

            // 2. If not, check if the user is an active member of the module
            ModuleMember moduleMember = getModuleMemberByOrgMemberAndModule(orgMember, module);
            if (!moduleMember.isActive()) {
                throw new PermissionDeniedException("You are no longer part of this module.");
            }

            // 3. If the user is an active member, check if they have permission to manage members
            ModulePermission modulePermission = modulePermissionService.getModulePermissionByModuleAndRole(module, moduleMember.getRole());
            if (!modulePermission.isManageMembers()) {
                throw new PermissionDeniedException("You do not have permission to manage members on this module.");
            }
        }

        // 4. Check if the user is already a member of the module
        if (moduleMemberRepository.existsByModule_PublicIdAndOrgMember_PublicId(moduleId, addModuleMemberDto.getUserId())) {
            throw new ActionProhibitedException("This user is already a member of the module.");
        }

        // 5. Check if the user to be added is part of the organization
        OrgMember memberToAdd = orgMemberService.getOrgMemberByPublicId(addModuleMemberDto.getUserId());
        if (!memberToAdd.getOrg().getId().equals(orgMember.getOrg().getId())) {
            throw new ActionProhibitedException("The member that you are trying to add does not belong to your organization.");
        }

        ModuleMember moduleMember = addModuleMemberDto.toModuleMember();
        moduleMember.setOrgMember(memberToAdd);
        moduleMember.setModule(module);
        moduleMemberRepository.save(moduleMember);
    }
}
