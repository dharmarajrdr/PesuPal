package com.pesupal.server.helpers;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.user.OrgMember;

public class ModuleHelper {

    /**
     * Checks if the given OrgMember is the owner of the specified Module.
     *
     * @param module
     * @param orgMember
     * @return
     */
    public static boolean isModuleOwner(Module module, OrgMember orgMember) {

        return module.getCreatedBy().getId().equals(orgMember.getId());
    }

    /**
     * Returns the attribute name for a ModuleField by replacing spaces with underscores and converting to lowercase.
     *
     * @param moduleField
     * @return
     */
    public static String getAttributeName(ModuleField moduleField) {

        return moduleField.getName().replace(" ", "_").toLowerCase();
    }
}
