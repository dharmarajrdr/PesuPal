package com.pesupal.server.service.implementations.module;

import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleMember;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.ModuleMemberRepository;
import com.pesupal.server.service.interfaces.module.ModuleMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModuleMemberServiceImpl implements ModuleMemberService {

    private final ModuleMemberRepository moduleMemberRepository;

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
}
