package com.pesupal.server.dto.response.chat.group_message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.enums.Visibility;
import com.pesupal.server.model.chat.group_message.Group;
import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

import java.net.URL;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupDto {

    private String id;

    private String name;

    private String description;

    private URL displayPicture;

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

    public static GroupDto fromGroupAndOrgMemberAndDisplayPicture(Group group, OrgMember orgMember, URL displayPicture) {
        GroupDto groupDto = fromGroupAndOrgMember(group, orgMember);
        groupDto.setDisplayPicture(displayPicture);
        return groupDto;
    }
}
