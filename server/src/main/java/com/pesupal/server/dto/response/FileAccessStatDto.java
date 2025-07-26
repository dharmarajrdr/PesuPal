package com.pesupal.server.dto.response;

import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.FileAccessStat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FileAccessStatDto extends UserBasicInfoDto {

    private LocalDateTime accessedAt;

    public static FileAccessStatDto fromFileAccessStatAndOrgMember(FileAccessStat fileAccessStat, OrgMember orgMember) {

        FileAccessStatDto fileAccessStatDto = new FileAccessStatDto();
        fileAccessStatDto.setAccessedAt(fileAccessStat.getCreatedAt());
        fileAccessStatDto.setDisplayName(orgMember.getDisplayName());
        fileAccessStatDto.setDisplayPicture(orgMember.getDisplayPicture());
        fileAccessStatDto.setUserId(orgMember.getUser().getPublicId());
        return fileAccessStatDto;
    }
}
