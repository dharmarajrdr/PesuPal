package com.pesupal.server.dto.response.group;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.enums.Visibility;
import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupDto {

    private String id;

    private String name;

    private String description;

    private UserPreviewDto owner;

    private Visibility visibility;

    private boolean active;

    public static GroupDto fromGroup(Group group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getPublicId());
        groupDto.setName(group.getName());
        groupDto.setDescription(group.getDescription());
        groupDto.setVisibility(group.getVisibility());
        groupDto.setActive(group.isActive());
        return groupDto;
    }

    public static GroupDto fromGroupAndOrgMember(Group group, OrgMember orgMember) {

        GroupDto groupDto = fromGroup(group);
        groupDto.setOwner(UserPreviewDto.fromOrgMember(orgMember));
        return groupDto;
    }
}
