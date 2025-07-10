package com.pesupal.server.strategies.workspace;

import com.pesupal.server.dto.request.CreateFolderDto;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.repository.FolderRepository;
import com.pesupal.server.service.interfaces.WorkdriveSpace;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component("PERSONAL_SPACE")
@AllArgsConstructor
public class PersonalSpace implements WorkdriveSpace {

    private final FolderRepository folderRepository;

    /**
     * Saves a folder in the personal space.
     *
     * @param folder
     * @return Folder
     */
    @Override
    public Folder save(Folder folder, CreateFolderDto createFolderDto, OrgMember orgMember) {

        return folderRepository.save(folder);
    }
}
