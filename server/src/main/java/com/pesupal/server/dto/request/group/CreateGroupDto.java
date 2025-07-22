package com.pesupal.server.dto.request.group;

import com.pesupal.server.enums.Visibility;
import com.pesupal.server.model.group.Group;
import lombok.Data;

@Data
public class CreateGroupDto {

    private String name;

    private String description;

    private Visibility visibility;

    public Group toGroup() {
        Group group = new Group();
        group.setName(name);
        group.setDescription(description);
        group.setVisibility(visibility);
        group.setActive(true);
        group.setShowOldMessagesToNewJoiners(true);
        return group;
    }
}
