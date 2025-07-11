package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.Security;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.File;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileDto extends FileOrFolderDto {

    private Long id;

    private String name;

    private UUID mediaId;

    private Security security;

    private UserBasicInfoDto owner;

    public static FileDto fromFileAndOrgMember(File file, OrgMember orgMember) {

        FileDto fileDto = new FileDto();
        fileDto.setId(file.getId());
        fileDto.setName(file.getName());
        fileDto.setMediaId(file.getMediaId());
        fileDto.setSecurity(file.getSecurity());
        fileDto.setOwner(UserBasicInfoDto.fromOrgMember(orgMember));
        return fileDto;
    }
}
