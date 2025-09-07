package com.pesupal.server.service.implementations.drive;

import com.pesupal.server.dto.request.drive.CreateFileDto;
import com.pesupal.server.dto.response.drive.FileDto;
import com.pesupal.server.dto.response.drive.FileOrFolderDto;
import com.pesupal.server.dto.response.drive.SpaceStatDto;
import com.pesupal.server.enums.Arithmetic;
import com.pesupal.server.enums.FileOrFolder;
import com.pesupal.server.enums.Workspace;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.helpers.FileHelper;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.File;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.repository.drive.FileRepository;
import com.pesupal.server.service.interfaces.drive.FileService;
import com.pesupal.server.service.interfaces.drive.FolderService;
import com.pesupal.server.service.interfaces.org.OrgMemberService;
import com.pesupal.server.strategies.media_storage.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class FileServiceImpl extends CurrentValueRetriever implements FileService {

    private final S3Service s3Service;
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

    /**
     * Retrieves space statistics for a given workspace.
     *
     * @param space
     * @return
     */
    @Override
    public Map<String, SpaceStatDto> getSpaceStats(Workspace space) {

        Map<String, SpaceStatDto> statDtoMap = new HashMap<>();
        List<File> files = fileRepository.findAllByCreator_OrgAndFolder_Space(getCurrentOrgMember().getOrg(), space);
        for (File file : files) {
            String extension = FileHelper.getExtensionGroup(file.getExtension());
            SpaceStatDto spaceStatDto = statDtoMap.getOrDefault(extension, new SpaceStatDto());
            if (!statDtoMap.containsKey(extension)) {
                spaceStatDto.setCount(1);
                spaceStatDto.setSize(file.getSize());
                statDtoMap.put(extension, spaceStatDto);
                continue;
            }
            spaceStatDto.setCount(spaceStatDto.getCount() + 1);
            spaceStatDto.setSize(spaceStatDto.getSize() + file.getSize());
        }
        return statDtoMap;
    }

    /**
     * Deletes a file by its ID.
     *
     * @param fileId
     */
    @Override
    @Transactional
    public void deleteFile(String fileId) {

        File file = getFileByPublicId(fileId);
        file.setDeleted(true);
        fileRepository.save(file);
        folderService.updateFolderSizeRecursively(file.getFolder(), file.getSize(), Arithmetic.MINUS);
    }

    /**
     * Performs garbage collection on media files that are no longer associated with any file records.
     * This method removes orphaned media files from the repository and s3 storage.
     */
    @Scheduled(cron = "${aws.s3.garbage-collection.cron}")
    public void garbageCollect() {

        List<File> deletedFiles = fileRepository.findAllByDeleted(true);
        for (File deletedFile : deletedFiles) {
            s3Service.deleteFile(deletedFile.getMediaId());
        }
        fileRepository.deleteAll(deletedFiles);
    }
}
