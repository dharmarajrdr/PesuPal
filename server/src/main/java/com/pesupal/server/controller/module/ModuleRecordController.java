package com.pesupal.server.controller.module;

import com.pesupal.server.dto.request.module.CreateModuleRecordDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.service.interfaces.module.ModuleRecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/module/record")
public class ModuleRecordController {

    private final ModuleRecordService moduleRecordService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createRecord(@RequestBody CreateModuleRecordDto createModuleRecordDto) {

        moduleRecordService.createRecord(createModuleRecordDto);
        return ResponseEntity.ok(new ApiResponseDto("Record created successfully"));
    }
}
