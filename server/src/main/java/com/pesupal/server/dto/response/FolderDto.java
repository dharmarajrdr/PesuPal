package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.Security;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.Folder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FolderDto extends FileOrFolderDto {

    private Long id;

    private String name;

    private Long size;

    private UserBasicInfoDto owner;

    private Security security;

    private List<FolderDto> subFolders;

    private List<FileDto> files;

    public static FolderDto fromFolderAndOrgMember(Folder folder, OrgMember orgMember) {

        FolderDto folderDto = new FolderDto();
        folderDto.setId(folder.getId());
        folderDto.setName(folder.getName());
        folderDto.setSize(folderDto.getSize());
        folderDto.setOwner(UserBasicInfoDto.fromOrgMember(orgMember));
        return folderDto;
    }
}
