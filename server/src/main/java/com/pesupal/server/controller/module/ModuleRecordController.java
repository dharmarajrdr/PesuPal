package com.pesupal.server.controller.module;

import com.pesupal.server.service.interfaces.module.ModuleRecordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/modules/record")
public class ModuleRecordController {

    private final ModuleRecordService moduleRecordService;
}
