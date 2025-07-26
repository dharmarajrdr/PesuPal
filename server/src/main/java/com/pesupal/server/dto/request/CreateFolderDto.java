package com.pesupal.server.dto.request;

import com.pesupal.server.enums.Security;
import com.pesupal.server.enums.Workspace;
import com.pesupal.server.model.workdrive.Folder;
import lombok.Data;

@Data
public class CreateFolderDto {

    private String name;

    private Workspace space = Workspace.PERSONAL_SPACE;

    private Security security = Security.NONE;

    private String parentFolderId;

    public Folder toFolder() {

        Folder folder = new Folder();
        folder.setName(this.name);
        folder.setSpace(this.space);
        return folder;
    }
}
