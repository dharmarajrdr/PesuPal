package com.pesupal.server.service.interfaces.drive;

import com.pesupal.server.enums.CRUD;
import com.pesupal.server.model.workdrive.PublicFolder;

public interface PublicFolderService {

    boolean allowToPerformCrudByDefault(PublicFolder publicFolder, CRUD crud);
}
