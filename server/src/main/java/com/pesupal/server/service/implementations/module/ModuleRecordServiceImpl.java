package com.pesupal.server.service.implementations.module;

import com.pesupal.server.repository.ModuleRecordRepository;
import com.pesupal.server.service.interfaces.module.ModuleRecordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModuleRecordServiceImpl implements ModuleRecordService {

    private final ModuleRecordRepository moduleRecordRepository;
}
