package com.pesupal.server.dto.request.chat.group_message;

import com.pesupal.server.enums.Visibility;
import com.pesupal.server.model.chat.group_message.Group;
import lombok.Data;

@Data
public class CreateGroupDto {

    private String name;

    private String description;

    private String displayPicture;

    private Boolean displayPictureRemoved;

    private Visibility visibility;

    public Group toGroup() {
        Group group = new Group();
        group.setName(name);
        group.setDescription(description);
        group.setVisibility(visibility);
        if (displayPictureRemoved != null && displayPictureRemoved) {
            displayPicture = null;
        }
        group.setDisplayPicture(displayPicture);
        group.setActive(true);
        group.setShowOldMessagesToNewJoiners(true);
        return group;
    }

    public void applyToGroup(Group group) {

        if (name != null) {
            group.setName(name.trim());
        }
        if (description != null) {
            group.setDescription(description.trim());
        }
        if (displayPicture != null) {
            group.setDisplayPicture(displayPicture.trim());
        }
        if (displayPictureRemoved != null) {
            if (displayPictureRemoved) {
                group.setDisplayPicture(null);
            }
        }
        if (visibility != null) {
            group.setVisibility(visibility);
        }
    }
}
