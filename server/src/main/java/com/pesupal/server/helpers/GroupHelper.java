package com.pesupal.server.helpers;

import com.pesupal.server.model.chat.group_message.Group;
import com.pesupal.server.model.user.OrgMember;

public class GroupHelper {

    /**
     * Checks if the given OrgMember is the owner of the specified Group.
     *
     * @param group
     * @param orgMember
     * @return
     */
    public static boolean isGroupOwner(Group group, OrgMember orgMember) {

        if (group == null || orgMember == null) {
            return false;
        }
        return group.getOwner() != null && group.getOwner().getId().equals(orgMember.getId());
    }
}
