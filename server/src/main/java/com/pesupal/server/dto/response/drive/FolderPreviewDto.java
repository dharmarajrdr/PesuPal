package com.pesupal.server.dto.response.drive;

import com.pesupal.server.enums.Security;
import com.pesupal.server.enums.Workspace;
import com.pesupal.server.model.workdrive.Folder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FolderPreviewDto {

    private String id;

    private String name;

    private Workspace space;

    private Security security;

    public static FolderPreviewDto fromFolder(Folder folder) {

        FolderPreviewDto folderPreviewDto = new FolderPreviewDto();
        folderPreviewDto.setId(folder.getPublicId());
        folderPreviewDto.setName(folder.getName());
        folderPreviewDto.setSpace(folder.getSpace());
        return folderPreviewDto;
    }
}
