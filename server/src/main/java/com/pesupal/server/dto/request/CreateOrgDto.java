package com.pesupal.server.dto.request;

import com.pesupal.server.model.org.Org;
import lombok.Data;

@Data
public class CreateOrgDto {

    private String displayName;

    private String uniqueName;

    public Org toOrg() {

        Org org = new Org();
        org.setDisplayName(this.displayName);
        org.setUniqueName(this.uniqueName);
        return org;
    }
}
