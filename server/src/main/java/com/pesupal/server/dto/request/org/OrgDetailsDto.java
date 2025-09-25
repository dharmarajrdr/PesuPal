package com.pesupal.server.dto.request.org;

import com.pesupal.server.model.org.Org;
import lombok.Data;

import java.util.UUID;

@Data
public class OrgDetailsDto {

    private String displayName;

    private String uniqueName;

    private UUID displayPicture;

    public Org toOrg() {

        Org org = new Org();
        org.setDisplayName(this.displayName);
        org.setUniqueName(this.uniqueName);
        org.setDisplayPicture(displayPicture);
        return org;
    }
}
