package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateFileDto;
import com.pesupal.server.dto.response.FileDto;
import com.pesupal.server.dto.response.FileOrFolderDto;
import com.pesupal.server.enums.Arithmetic;
import com.pesupal.server.enums.FileOrFolder;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.File;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.repository.FileRepository;
import com.pesupal.server.service.interfaces.FileService;
import com.pesupal.server.service.interfaces.FolderService;
import com.pesupal.server.service.interfaces.MediaService;
import com.pesupal.server.service.interfaces.OrgMemberService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final MediaService mediaService;
    private final FolderService folderService;
    private final FileRepository fileRepository;
    private final OrgMemberService orgMemberService;

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

    /**
     * Creates a new file based on the provided DTO and associates it with the user
     * and organization.
     *
     * @param createFileDto
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    @Transactional
    public FileDto createFile(CreateFileDto createFileDto, Long userId, Long orgId) throws Exception {

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);

        Folder folder = folderService.getFolderById(createFileDto.getFolderId());

        Long size = mediaService.getFileSizeInKB(createFileDto.getMediaId());

        File file = createFileDto.toFile();
        file.setCreator(orgMember.getUser());
        file.setFolder(folder);
        file.setSize(size);
        file = fileRepository.save(file);
        folderService.updateFolderSizeRecursively(folder, size, Arithmetic.PLUS);
        return FileDto.fromFile(file);
    }

}
