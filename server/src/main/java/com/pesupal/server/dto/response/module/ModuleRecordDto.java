package com.pesupal.server.dto.response.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.model.module.ModuleRecord;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleRecordDto {

    private String moduleId;

    private String recordId;

    private String subject;

    private UserPreviewDto createdBy;

    private LocalDateTime createdAt;

    private UserPreviewDto updatedBy;

    private LocalDateTime lastUpdatedAt;

    private List<ModuleFieldDto> fields = new ArrayList<>();

    public static ModuleRecordDto fromModuleRecord(ModuleRecord moduleRecord) {

        ModuleRecordDto moduleRecordDto = new ModuleRecordDto();
        moduleRecordDto.setModuleId(moduleRecord.getModule().getPublicId());
        moduleRecordDto.setRecordId(moduleRecord.getPublicId());
        moduleRecordDto.setSubject(moduleRecord.getSubject());
        moduleRecordDto.setCreatedBy(UserPreviewDto.fromOrgMember(moduleRecord.getCreatedBy()));
        moduleRecordDto.setCreatedAt(moduleRecord.getCreatedAt());
        moduleRecordDto.setUpdatedBy(UserPreviewDto.fromOrgMember(moduleRecord.getUpdatedBy()));
        moduleRecordDto.setLastUpdatedAt(moduleRecord.getUpdatedAt());
        return moduleRecordDto;
    }
}
