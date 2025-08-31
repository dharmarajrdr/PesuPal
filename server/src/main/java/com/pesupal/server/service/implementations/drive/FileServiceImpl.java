package com.pesupal.server.service.implementations.drive;

import com.pesupal.server.dto.request.drive.CreateFileDto;
import com.pesupal.server.dto.response.drive.FileDto;
import com.pesupal.server.dto.response.drive.FileOrFolderDto;
import com.pesupal.server.enums.Arithmetic;
import com.pesupal.server.enums.FileOrFolder;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.File;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.repository.drive.FileRepository;
import com.pesupal.server.service.interfaces.drive.FileService;
import com.pesupal.server.service.interfaces.drive.FolderService;
import com.pesupal.server.service.interfaces.org.OrgMemberService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FileServiceImpl extends CurrentValueRetriever implements FileService {

    private final FolderService folderService;
    private final FileRepository fileRepository;
    private final OrgMemberService orgMemberService;

    /**
     * Converts a File and OrgMember to a FileDto.
     *
     * @param file
     * @param owner
     * @return
     */
    @Override
    public FileDto fromFileAndOrgMember(File file, OrgMember owner) {

        FileDto fileDto = FileDto.fromFile(file);
        fileDto.setOwner(orgMemberService.getUserBasicInfo(owner));
        return fileDto;
    }

    /**
     * Finds all files in a given folder for a specific organization member.
     *
     * @param parentFolder
     * @return
     */
    @Override
    public List<FileOrFolderDto> findAllByFolderAndOrgMemberAndDeleted(Folder parentFolder, OrgMember orgMember, boolean deleted) {

        Sort sort = Sort.by(Sort.Order.asc("name").ignoreCase());
        return fileRepository.findAllByFolderAndDeleted(parentFolder, deleted, sort).stream().map(file -> {
            FileOrFolderDto fileDto = fromFileAndOrgMember(file, file.getCreator());
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
    public FileDto createFile(CreateFileDto createFileDto) {

        OrgMember orgMember = getCurrentOrgMember();

        Folder folder = folderService.getFolderByPublicId(createFileDto.getFolderId());

        File file = createFileDto.toFile();
        file.setCreator(orgMember);
        file.setFolder(folder);
        file = fileRepository.save(file);
        folderService.updateFolderSizeRecursively(folder, file.getSize(), Arithmetic.PLUS);
        FileDto fileDto = FileDto.fromFile(file);
        fileDto.setOwner(orgMemberService.getUserBasicInfo(orgMember));
        return fileDto;
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

    /**
     * Retrieves a file by its public ID.
     *
     * @param publicId
     * @return
     */
    @Override
    public File getFileByPublicId(String publicId) {

        return fileRepository.findByPublicId(publicId).orElseThrow(() -> new DataNotFoundException("File with public ID " + publicId + " not found."));
    }

}
