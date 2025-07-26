package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.response.FileDto;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.model.workdrive.File;
import com.pesupal.server.model.workdrive.StarredFile;
import com.pesupal.server.repository.StarredFileRepository;
import com.pesupal.server.service.interfaces.FileService;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.StarredFileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StarredFileServiceImpl implements StarredFileService {

    private final FileService fileService;
    private final OrgMemberService orgMemberService;
    private final StarredFileRepository starredFileRepository;

    /**
     * Checks if a file is already starred by a user.
     *
     * @param file
     * @param user
     * @return boolean indicating if the file is starred by the user
     */
    @Override
    public boolean existsByFileAndUser(File file, User user) {

        return starredFileRepository.existsByFileAndUser(file, user);
    }

    /**
     * Adds a file to the user's starred files list.
     *
     * @param fileId
     * @param userId
     * @param orgId
     */
    @Override
    public void addStarredFile(Long fileId) {

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);
        User user = orgMember.getUser();
        File file = fileService.getFileByIdAndOrgId(fileId, orgId);

        if (existsByFileAndUser(file, user)) {
            throw new ActionProhibitedException("You have already starred this file.");
        }

        StarredFile starredFile = new StarredFile();
        starredFile.setUser(user);
        starredFile.setFile(file);
        starredFileRepository.save(starredFile);
    }

    /**
     * Retrieves a list of starred files for a user in a specific organization.
     *
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public List<FileDto> getStarredFiles(Long userId, Long orgId) {
        return List.of();
    }
}
