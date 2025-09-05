package com.pesupal.server.dto.request.org;

import com.pesupal.server.enums.OrgAction;
import lombok.Data;

@Data
public class OrgConfigurationDto {

    private Long roleId;

    private OrgAction action;
}
