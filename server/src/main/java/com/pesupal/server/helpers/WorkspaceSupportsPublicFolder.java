package com.pesupal.server.helpers;

import com.pesupal.server.dto.request.CreateFolderDto;
import com.pesupal.server.enums.Security;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.model.workdrive.PublicFolder;

public abstract class WorkspaceSupportsPublicFolder {

    protected PublicFolder getPublicFolder(Folder folder, CreateFolderDto createFolderDto) {
        PublicFolder publicFolder = new PublicFolder();
        publicFolder.setFolder(folder);
        publicFolder.setSecurity(createFolderDto.getSecurity());
        Security security = createFolderDto.getSecurity();
        applySecuritySettings(publicFolder, security);
        return publicFolder;
    }

    /**
     * Default security settings for public folders.
     *
     * @param publicFolder
     * @param security
     */
    protected void applySecuritySettings(PublicFolder publicFolder, Security security) {
        switch (security) {
            case NONE: {
                publicFolder.setReadable(true);
                publicFolder.setWritable(true);
                publicFolder.setDeletable(false);
                break;
            }
            case SECURED: {
                publicFolder.setReadable(false);
                publicFolder.setWritable(false);
                publicFolder.setDeletable(false);
                break;
            }
        }
    }
}
