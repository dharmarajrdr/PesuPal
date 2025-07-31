package com.pesupal.server.service.interfaces.module;

import com.pesupal.server.dto.request.module.CreateModuleRecordDto;
import com.pesupal.server.dto.response.module.ModuleRecordDto;

public interface ModuleRecordService {

    ModuleRecordDto createRecord(CreateModuleRecordDto createModuleRecordDto);
}
