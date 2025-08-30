package com.pesupal.server.dto.request.module;

import lombok.Data;

import java.util.Map;

@Data
public class CreateModuleRecordDto {

    private String moduleId;

    private Map<String, Object> data;
}
