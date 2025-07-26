package com.pesupal.server.service.implementations;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.dto.response.FileAccessStatDto;
import com.pesupal.server.dto.response.FileDto;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.File;
import com.pesupal.server.repository.FileAccessStatRepository;
import com.pesupal.server.service.interfaces.FileAccessStatService;
import com.pesupal.server.service.interfaces.FileService;
import com.pesupal.server.service.interfaces.OrgMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class FileAccessStatServiceImpl implements FileAccessStatService {

    private final FileService fileService;
    private final OrgMemberService orgMemberService;
    private final FileAccessStatRepository fileAccessStatRepository;

    /**
     * Retrieves file access statistics for a specific file and user within an organization.
     *
     * @param fileId
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public List<FileAccessStatDto> getFileAccessStats(Long fileId) {

        Map<Long, OrgMember> memo = new HashMap<>();

        File file = fileService.getFileByIdAndOrgId(fileId, orgId);

        if (!file.getCreator().getId().equals(userId)) {
            throw new PermissionDeniedException("You do not have permission to view this file's access statistics.");
        }

        return fileAccessStatRepository.findAllByFileIdOrderByCreatedAtDesc(fileId).stream().map(fileAccessStat -> {
            Long accessedById = fileAccessStat.getUser().getId();
            OrgMember orgMember = memo.computeIfAbsent(accessedById, id -> orgMemberService.getOrgMemberByUserIdAndOrgId(id, orgId));
            return FileAccessStatDto.fromFileAccessStatAndOrgMember(fileAccessStat, orgMember);
        }).toList();

    }

    /**
     * Retrieves a list of recently accessed files by the current user within the organization.
     *
     * @param userId
     * @param orgId
     * @return List of FileDto representing recently accessed files
     */
    @Override
    public List<FileDto> getRecentlyAccessedFiles(Long userId, Long orgId) {

        orgMemberService.validateUserIsOrgMember(userId, orgId);

        Map<Long, OrgMember> memo = new HashMap<>();
        return fileAccessStatRepository.findRecentlyAccessedFilesByUserIdLimit(userId, StaticConfig.MAXIMUM_RECENTLY_ACCESSED_FILES).stream().map(fileAccessStat -> {
            Long accessedById = fileAccessStat.getUser().getId();
            OrgMember orgMember = memo.computeIfAbsent(accessedById, id -> orgMemberService.getOrgMemberByUserIdAndOrgId(id, orgId));
            return FileDto.fromFileAndOrgMember(fileAccessStat.getFile(), orgMember);
        }).toList();
    }
}
