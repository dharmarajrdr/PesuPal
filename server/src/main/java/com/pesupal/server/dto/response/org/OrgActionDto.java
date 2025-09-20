package com.pesupal.server.dto.response.org;

import com.pesupal.server.enums.OrgAction;

public record OrgActionDto(int actionId, String title) {

    public static OrgActionDto fromOrgAction(OrgAction action) {
        
        return new OrgActionDto(action.getActionId(), action.getTitle());
    }
}
