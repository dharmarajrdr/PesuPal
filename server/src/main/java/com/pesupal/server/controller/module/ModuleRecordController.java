package com.pesupal.server.controller.module;

import com.pesupal.server.dto.request.SortColumnDto;
import com.pesupal.server.dto.request.module.CreateModuleRecordDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.module.ModuleRecordDto;
import com.pesupal.server.service.interfaces.module.ModuleRecordService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/module")
public class ModuleRecordController {

    private final ModuleRecordService moduleRecordService;

    @PostMapping("/record/create")
    public ResponseEntity<ApiResponseDto> createRecord(@RequestBody CreateModuleRecordDto createModuleRecordDto) {

        moduleRecordService.createRecord(createModuleRecordDto);
        return ResponseEntity.ok(new ApiResponseDto("Record created successfully"));
    }

    @GetMapping("/record/{recordId}")
    public ResponseEntity<ApiResponseDto> getRecordById(@PathVariable String recordId) {

        // Detail view of a record
        ModuleRecordDto moduleRecordDto = moduleRecordService.getRecordById(recordId);
        return ResponseEntity.ok().body(new ApiResponseDto("Record retrieved successfully", moduleRecordDto));
    }

    @GetMapping("/records")
    public ResponseEntity<ApiResponseDto> getAllRecords(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) SortColumnDto sortColumnDto,
            Sort sort) {

        // List view of records
        List<ModuleRecordDto> records = moduleRecordService.getAllRecords(page, size, sortColumnDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Records retrieved successfully", records));
    }
}
