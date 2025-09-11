package com.pesupal.server.helpers;

import com.pesupal.server.model.org.Org;

public class OrgHelper {

    public static boolean isOrgOwner(String userPublicId, Org org) {

        return userPublicId.equals(org.getOwner().getPublicId());
    }
}
