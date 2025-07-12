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

    private Long size;

    private UUID mediaId;

    private Security security;

    private UserBasicInfoDto owner;

    private int accessCount;

    public static FileDto fromFileAndOrgMember(File file, OrgMember orgMember) {

        FileDto fileDto = fromFile(file);
        fileDto.setOwner(UserBasicInfoDto.fromOrgMember(orgMember));
        return fileDto;
    }

    public static FileDto fromFile(File file) {

        FileDto fileDto = new FileDto();
        fileDto.setId(file.getId());
        fileDto.setName(file.getName());
        fileDto.setSize(file.getSize());
        fileDto.setMediaId(file.getMediaId());
        fileDto.setSecurity(file.getSecurity());
        fileDto.setAccessCount(file.getAccessStats().size());
        return fileDto;
    }
}
