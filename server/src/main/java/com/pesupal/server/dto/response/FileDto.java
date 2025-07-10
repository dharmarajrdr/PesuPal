package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.Security;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileDto {

    private String name;

    private UUID uniqueId;

    private Security security;

    private UserBasicInfoDto owner;
}
