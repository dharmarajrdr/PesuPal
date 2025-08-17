package com.pesupal.server.dto.response.drive;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.enums.Security;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.Folder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FolderDto extends FileOrFolderDto {

    private String id;

    private String name;

    private Long size;

    private UserBasicInfoDto owner;

    private Security security;

    private List<FolderDto> subFolders;

    private int files;

    public static FolderDto fromFolderAndOrgMember(Folder folder, OrgMember orgMember) {

        FolderDto folderDto = new FolderDto();
        folderDto.setId(folder.getPublicId());
        folderDto.setName(folder.getName());
        folderDto.setSize(folder.getSize());
        folderDto.setOwner(UserBasicInfoDto.fromOrgMember(orgMember));
        folderDto.setFiles(folder.getFiles().size());
        return folderDto;
    }
}
