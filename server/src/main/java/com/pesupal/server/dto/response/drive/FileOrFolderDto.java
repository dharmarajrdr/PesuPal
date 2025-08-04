package com.pesupal.server.dto.response.drive;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.FileOrFolder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileOrFolderDto {

    private FileOrFolder type;
}
