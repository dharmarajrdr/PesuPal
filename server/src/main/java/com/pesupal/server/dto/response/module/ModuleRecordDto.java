package com.pesupal.server.dto.response.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.module.ModuleRecord;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleRecordDto {

    private String moduleId;

    private String recordId;

    private List<ModuleFieldDto> fields = new ArrayList<>();

    public static ModuleRecordDto fromModuleRecord(ModuleRecord moduleRecord) {

        ModuleRecordDto moduleRecordDto = new ModuleRecordDto();
        moduleRecordDto.setModuleId(moduleRecord.getModule().getPublicId());
        moduleRecordDto.setRecordId(moduleRecord.getPublicId());
        return moduleRecordDto;
    }
}
