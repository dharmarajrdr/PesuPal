package com.pesupal.server.controller.module;

import com.pesupal.server.dto.request.SortColumnDto;
import com.pesupal.server.dto.request.module.CreateModuleRecordDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.PaginatedData;
import com.pesupal.server.dto.response.module.ModuleRecordDto;
import com.pesupal.server.model.module.ModuleView;
import com.pesupal.server.service.interfaces.module.ModuleRecordService;
import lombok.AllArgsConstructor;
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
        ModuleRecordDto moduleRecordDto = moduleRecordService.getRecordById(recordId, ModuleView.DETAIL_VIEW);
        return ResponseEntity.ok().body(new ApiResponseDto("Record retrieved successfully", moduleRecordDto));
    }

    @DeleteMapping("/record/{recordId}")
    public ResponseEntity<ApiResponseDto> deleteRecordById(@PathVariable String recordId) {

        moduleRecordService.deleteRecord(recordId);
        return ResponseEntity.ok(new ApiResponseDto("Record deleted successfully"));
    }

    @GetMapping("{moduleId}/records")
    public ResponseEntity<ApiResponseDto> getAllRecords(
            @PathVariable String moduleId,
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) SortColumnDto sortColumnDto) {

        // List view of records
        PaginatedData<List<ModuleRecordDto>> paginatedData = moduleRecordService.getAllRecords(moduleId, page, size, sortColumnDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Records retrieved successfully", paginatedData.getData(), paginatedData.getInfo()));
    }

    @DeleteMapping("/{moduleId}/records")
    public ResponseEntity<ApiResponseDto> deleteAllRecords(@PathVariable String moduleId) {

        moduleRecordService.deleteAllRecords(moduleId);
        return ResponseEntity.ok(new ApiResponseDto("All records deleted successfully"));
    }
}
