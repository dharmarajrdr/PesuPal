package com.pesupal.server.service.interfaces.module;

import com.pesupal.server.dto.request.SortColumnDto;
import com.pesupal.server.dto.request.module.CreateModuleRecordDto;
import com.pesupal.server.dto.response.PaginatedData;
import com.pesupal.server.dto.response.module.ModuleRecordDto;
import com.pesupal.server.model.module.ModuleView;

import java.util.List;

public interface ModuleRecordService {

    ModuleRecordDto createRecord(CreateModuleRecordDto createModuleRecordDto);

    void deleteRecord(String recordId);

    ModuleRecordDto getRecordById(String recordId, ModuleView moduleView);

    PaginatedData<List<ModuleRecordDto>> getAllRecords(String moduleId, int page, int size, SortColumnDto sortColumnDto);

    void deleteAllRecords(String moduleId);
}
