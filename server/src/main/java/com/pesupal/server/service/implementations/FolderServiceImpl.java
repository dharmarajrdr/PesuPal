package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateFolderDto;
import com.pesupal.server.dto.response.FolderDto;
import com.pesupal.server.enums.Workspace;
import com.pesupal.server.service.interfaces.FolderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FolderServiceImpl implements FolderService {

    /**
     * Creates a new folder in the specified workspace.
     *
     * @param createFolderDto
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public FolderDto createFolder(CreateFolderDto createFolderDto, Long userId, Long orgId) {
        return null;
    }

    /**
     * Retrieves all folders in the specified workspace for a user.
     *
     * @param space
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public List<FolderDto> getAllFolders(Workspace space, Long userId, Long orgId) {
        return List.of();
    }

    /**
     * Retrieves a folder by its ID in the specified workspace for a user.
     *
     * @param folderId
     * @param userId
     * @param orgId
     */
    @Override
    public void deleteFolder(Long folderId, Long userId, Long orgId) {

    }
}
