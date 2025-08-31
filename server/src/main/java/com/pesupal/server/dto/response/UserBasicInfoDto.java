package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.MemberStatus;
import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

import java.net.URL;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBasicInfoDto {

    private String userId;

    private String displayName;

    private URL displayPicture;

    private String designation;

    private String department;

    private MemberStatus status;

    private String email;

    private String phone;

    private Integer employeeId;

    private String chatId;

    public static UserBasicInfoDto fromOrgMember(OrgMember orgMember) {

        UserBasicInfoDto userBasicInfoDto = new UserBasicInfoDto();
        userBasicInfoDto.setUserId(orgMember.getPublicId());
        userBasicInfoDto.setDisplayName(orgMember.getDisplayName());
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
