package com.pesupal.server.dto.response.group;

import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.enums.Visibility;
import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

@Data
public class GroupDto {

    private Long id;

    private String name;

    private String description;

    private UserPreviewDto owner;

    private Visibility visibility;

    private boolean active;

    public static GroupDto fromGroup(Group group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setName(group.getName());
        groupDto.setDescription(group.getDescription());
        groupDto.setVisibility(group.getVisibility());
        groupDto.setActive(group.isActive());
        return groupDto;
    }

    public static GroupDto fromGroupAndOrgMember(Group group, OrgMember orgMember) {

        GroupDto groupDto = fromGroup(group);
        groupDto.setId(orgMember.getId());
        groupDto.setOwner(UserPreviewDto.fromOrgMember(orgMember));
        return groupDto;
    }
}
