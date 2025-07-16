package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBasicInfoDto {

    private Long userId;

    private String displayName;

    private String displayPicture;

    private String designation;

    private String department;

    private String status;

    private String email;

    private String phone;

    private Integer employeeId;

    public static UserBasicInfoDto fromOrgMember(OrgMember orgMember) {

        UserBasicInfoDto userBasicInfoDto = new UserBasicInfoDto();
        userBasicInfoDto.setUserId(orgMember.getUser().getId());
        userBasicInfoDto.setDisplayName(orgMember.getDisplayName());
        userBasicInfoDto.setDisplayPicture(orgMember.getDisplayPicture());
        userBasicInfoDto.setDesignation(orgMember.getDesignation().getName());
        userBasicInfoDto.setDepartment(orgMember.getDepartment().getName());
        userBasicInfoDto.setStatus(orgMember.getStatus());
        userBasicInfoDto.setEmail(orgMember.getUser().getEmail());
        userBasicInfoDto.setPhone(orgMember.getUser().getPhone());
        if (orgMember.getOrg().isShowEmployeeId()) {
            userBasicInfoDto.setEmployeeId(orgMember.getEmployeeId());
        }
        return userBasicInfoDto;
    }
}
