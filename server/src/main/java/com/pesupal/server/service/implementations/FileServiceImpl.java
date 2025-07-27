package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateFileDto;
import com.pesupal.server.dto.response.FileDto;
import com.pesupal.server.dto.response.FileOrFolderDto;
import com.pesupal.server.enums.Arithmetic;
import com.pesupal.server.enums.FileOrFolder;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.File;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.repository.FileRepository;
import com.pesupal.server.service.interfaces.FileService;
import com.pesupal.server.service.interfaces.FolderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FileServiceImpl extends CurrentValueRetriever implements FileService {

    private final FolderService folderService;
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

    /**
     * Creates a new file based on the provided DTO and associates it with the user
     * and organization.
     *
     * @param createFileDto
     * @return
     */
    @Override
    @Transactional
    public FileDto createFile(CreateFileDto createFileDto) throws Exception {

        OrgMember orgMember = getCurrentOrgMember();

        Folder folder = folderService.getFolderById(createFileDto.getFolderId());

        // Long size = mediaService.getFileSizeInKB(createFileDto.getMediaId());
        Long size = 0L;

        File file = createFileDto.toFile();
        file.setCreator(orgMember);
        file.setFolder(folder);
        file.setSize(size);
        file = fileRepository.save(file);
        folderService.updateFolderSizeRecursively(folder, size, Arithmetic.PLUS);
        return FileDto.fromFile(file);
    }

    /**
     * Retrieves a file by its ID and organization ID.
     *
     * @param fileId
     * @param orgId
     * @return
     */
    @Override
    public File getFileByIdAndOrgId(Long fileId, Long orgId) {

        return fileRepository.findByIdAndCreator_OrgId(fileId, orgId).orElseThrow(() -> new DataNotFoundException("File with ID " + fileId + " not found."));
    }

}
