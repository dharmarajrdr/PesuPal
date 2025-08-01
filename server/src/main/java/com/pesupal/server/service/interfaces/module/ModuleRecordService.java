package com.pesupal.server.service.interfaces.module;

import com.pesupal.server.dto.request.SortColumnDto;
import com.pesupal.server.dto.request.module.CreateModuleRecordDto;
import com.pesupal.server.dto.response.module.ModuleRecordDto;

import java.util.List;

public interface ModuleRecordService {

    ModuleRecordDto createRecord(CreateModuleRecordDto createModuleRecordDto);

    ModuleRecordDto getRecordById(String recordId);

    List<ModuleRecordDto> getAllRecords(Integer page, Integer size, SortColumnDto sortColumnDto);
}
