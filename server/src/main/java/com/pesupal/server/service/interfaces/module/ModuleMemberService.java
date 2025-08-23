package com.pesupal.server.service.interfaces.module;

import com.pesupal.server.dto.request.module.AddModuleMemberDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.module.ModuleMemberDto;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleMember;
import com.pesupal.server.model.user.OrgMember;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ModuleMemberService {

    ModuleMember getModuleMemberByOrgMemberAndModule(OrgMember orgMember, Module module);

    void addOrgOwnerToModule(Module module, OrgMember orgMember);

    void addMemberToModule(String moduleId, AddModuleMemberDto addModuleMemberDto);

    List<Module> getAllModulesOfOrgMember(OrgMember orgMember);

    void deleteAllMembersInModule(String moduleId);

    List<UserPreviewDto> getNonMembersOfModule(String moduleId, String search, Pageable pageable);

    List<ModuleMemberDto> getMembersOfModule(String moduleId, String search, Pageable pageable);
}
