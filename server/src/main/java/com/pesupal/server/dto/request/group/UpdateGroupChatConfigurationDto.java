package com.pesupal.server.dto.request.group;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.Role;
import com.pesupal.server.model.group.GroupChatConfiguration;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateGroupChatConfigurationDto {

    private Long groupId;

    private Role role;

    private Boolean addMember;

    private Boolean removeMember;

    private Boolean changeName;

    private Boolean changeDescription;

    private Boolean deleteGroup;

    private Boolean leaveGroup;

    private Boolean changeProfilePicture;

    private Boolean viewMembers;

    private Boolean postMessage;

    private Boolean pinMessage;

    private Boolean deleteMessage;

    private Boolean clearChat;

    private Boolean roleUpdate;

    public void applyToGroupChatConfiguration(GroupChatConfiguration groupChatConfiguration) {

        if (addMember != null) {
            groupChatConfiguration.setAddMember(addMember);
        }
        if (removeMember != null) {
            groupChatConfiguration.setRemoveMember(removeMember);
        }
        if (changeName != null) {
            groupChatConfiguration.setChangeName(changeName);
        }
        if (changeDescription != null) {
            groupChatConfiguration.setChangeDescription(changeDescription);
        }
        if (deleteGroup != null) {
            groupChatConfiguration.setDeleteGroup(deleteGroup);
        }
        if (leaveGroup != null) {
            groupChatConfiguration.setLeaveGroup(leaveGroup);
        }
        if (changeProfilePicture != null) {
            groupChatConfiguration.setChangeProfilePicture(changeProfilePicture);
        }
        if (viewMembers != null) {
            groupChatConfiguration.setViewMembers(viewMembers);
        }
        if (postMessage != null) {
            groupChatConfiguration.setPostMessage(postMessage);
        }
        if (pinMessage != null) {
            groupChatConfiguration.setPinMessage(pinMessage);
        }
        if (deleteMessage != null) {
            groupChatConfiguration.setDeleteMessage(deleteMessage);
        }
        if (clearChat != null) {
            groupChatConfiguration.setClearChat(clearChat);
        }
        if (roleUpdate != null) {
            groupChatConfiguration.setRoleUpdate(roleUpdate);
        }
    }
}
