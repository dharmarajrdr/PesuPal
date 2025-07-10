package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.Security;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FolderDto {

    private Long id;

    private String name;

    private Security security;

    private UserBasicInfoDto owner;

    private List<FolderDto> subFolders;

    private List<FileDto> files;
}
