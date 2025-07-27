package com.pesupal.server.dto.request;

import com.pesupal.server.enums.Role;
import lombok.Data;

@Data
public class AddOrgMemberDto {

    Long userId;

    String userName;

    String displayName;

    Long designationId;

    Long departmentId;

    String managerId;

    Role role;
}
