package com.pesupal.server.controller.module;

import com.pesupal.server.dto.request.module.AddModuleFieldDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.service.interfaces.module.ModuleFieldService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/module")
public class ModuleFieldController {

    private final ModuleFieldService moduleFieldService;

    @PostMapping("/field")
    public ResponseEntity<ApiResponseDto> addModuleField(@RequestBody AddModuleFieldDto addModuleFieldDto) {

        ModuleFieldDto moduleFieldDtos = moduleFieldService.addModuleField(addModuleFieldDto);
        return ResponseEntity.ok(new ApiResponseDto("Field added successfully", moduleFieldDtos));
    }

    @GetMapping("/{moduleId}/fields")
    public ResponseEntity<ApiResponseDto> getModuleFields(@PathVariable String moduleId) {

        List<ModuleFieldDto> moduleFieldDtos = moduleFieldService.getModuleFields(moduleId);
        return ResponseEntity.ok(new ApiResponseDto("Module fields retrieved successfully", moduleFieldDtos));
    }

    @DeleteMapping("/field/{fieldId}")
    public ResponseEntity<ApiResponseDto> deleteModuleField(@PathVariable Long fieldId) {

        moduleFieldService.deleteModuleField(fieldId);
        return ResponseEntity.ok(new ApiResponseDto("Field deleted successfully"));
    }
}
