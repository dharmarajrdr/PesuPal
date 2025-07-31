package com.pesupal.server.service.interfaces.module;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleMember;
import com.pesupal.server.model.user.OrgMember;

public interface ModuleMemberService {

    ModuleMember getModuleMemberByOrgMemberAndModule(OrgMember orgMember, Module module);
}
