package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.response.FileDto;
import com.pesupal.server.dto.response.FileOrFolderDto;
import com.pesupal.server.enums.FileOrFolder;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.repository.FileRepository;
import com.pesupal.server.service.interfaces.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    /**
     * Finds all files in a given folder for a specific organization member.
     *
     * @param parentFolder
     * @return
     */
    @Override
    public List<FileOrFolderDto> findAllByFolderAndOrgMember(Folder parentFolder, OrgMember orgMember) {

        return fileRepository.findAllByFolder(parentFolder).stream().map(file -> {
            FileOrFolderDto fileDto = FileDto.fromFileAndOrgMember(file, orgMember);
            fileDto.setType(FileOrFolder.FILE);
            return fileDto;
        }).toList();
    }
}
