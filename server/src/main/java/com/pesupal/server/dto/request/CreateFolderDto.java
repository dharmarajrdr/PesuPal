package com.pesupal.server.dto.request;

import com.pesupal.server.enums.Security;
import com.pesupal.server.enums.Workspace;
import lombok.Data;

@Data
public class CreateFolderDto {

    private String name;

    private Workspace space;

    private Security security;

    private Long parentFolderId;
}
