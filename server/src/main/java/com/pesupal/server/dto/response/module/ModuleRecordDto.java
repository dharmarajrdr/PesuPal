package com.pesupal.server.dto.response.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.module.ModuleRecord;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleRecordDto {

    private String moduleId;

    private String recordId;

    private String subject;

    private LocalDateTime lastUpdatedAt;

    public static ModuleRecordDto fromModuleRecord(ModuleRecord moduleRecord) {

        ModuleRecordDto moduleRecordDto = new ModuleRecordDto();
        moduleRecordDto.setModuleId(moduleRecord.getModule().getPublicId());
        moduleRecordDto.setRecordId(moduleRecord.getPublicId());
        moduleRecordDto.setSubject(moduleRecord.getSubject());
        moduleRecordDto.setLastUpdatedAt(moduleRecord.getUpdatedAt());
        return moduleRecordDto;
    }
}
