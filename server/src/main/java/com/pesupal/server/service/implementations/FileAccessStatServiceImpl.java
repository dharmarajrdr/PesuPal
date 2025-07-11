package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.response.FileAccessStatDto;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.File;
import com.pesupal.server.repository.FileAccessStatRepository;
import com.pesupal.server.repository.FileRepository;
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

    private final FileAccessStatRepository fileAccessStatRepository;
    private final OrgMemberService orgMemberService;
    private final FileRepository fileRepository;
    private final FileService fileService;

    /**
     * Retrieves file access statistics for a specific file and user within an organization.
     *
     * @param fileId
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public List<FileAccessStatDto> getFileAccessStats(Long fileId, Long userId, Long orgId) {

        Map<Long, OrgMember> memo = new HashMap<>();

        File file = fileService.getFileByIdAndOrgId(fileId, orgId);

        if (!file.getCreator().getId().equals(userId)) {
            throw new PermissionDeniedException("You do not have permission to view this file's access statistics.");
        }

        return fileAccessStatRepository.findAllByFileIdOrderByCreatedAtDesc(fileId).stream().map(fileAccessStat -> {
            Long accessedById = fileAccessStat.getUser().getId();
            if (!memo.containsKey(accessedById)) {
                memo.put(accessedById, orgMemberService.getOrgMemberByUserIdAndOrgId(accessedById, orgId));
            }
            return FileAccessStatDto.fromFileAccessStatAndOrgMember(fileAccessStat, memo.get(accessedById));
        }).toList();

    }
}
