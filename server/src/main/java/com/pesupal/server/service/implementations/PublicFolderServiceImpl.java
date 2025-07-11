package com.pesupal.server.service.implementations;

import com.pesupal.server.enums.CRUD;
import com.pesupal.server.model.workdrive.PublicFolder;
import com.pesupal.server.repository.PublicFolderRepository;
import com.pesupal.server.service.interfaces.PublicFolderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PublicFolderServiceImpl implements PublicFolderService {

    private final PublicFolderRepository publicFolderRepository;

    /**
     * Checks if the public folder is open to perform a specific action based on the CRUD operation.
     *
     * @param publicFolder
     * @param crud
     * @return boolean
     */
    @Override
    public boolean allowToPerformCrudByDefault(PublicFolder publicFolder, CRUD crud) {

        return switch (crud) {
            case CREATE, UPDATE, DELETE -> publicFolder.isWritable();
            case READ -> publicFolder.isReadable();
        };
    }
}
