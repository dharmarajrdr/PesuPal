package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.user.OrgMember;

public interface GroupChatMemberService {

    void initializeGroupChatMember(Group group, OrgMember orgMember);
}
