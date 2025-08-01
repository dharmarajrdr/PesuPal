package com.pesupal.server.controller.module;

import com.pesupal.server.dto.request.module.CreateModuleDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.module.ModuleDto;
import com.pesupal.server.dto.response.module.ModulePreviewDto;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.service.interfaces.module.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/module")
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createModule(@RequestBody CreateModuleDto createModuleDto) {

        Module module = moduleService.createModule(createModuleDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Module created successfully", module));
    }

    @GetMapping("/{moduleId}")
    public ResponseEntity<ApiResponseDto> getModuleById(@PathVariable String moduleId) {

        ModuleDto moduleDto = moduleService.getModuleDtoById(moduleId);
        return ResponseEntity.ok().body(new ApiResponseDto("Module retrieved successfully", moduleDto));
    }

    @PatchMapping("/{moduleId}/publish")
    public ResponseEntity<ApiResponseDto> publishModule(@PathVariable String moduleId) {

        moduleService.publishModule(moduleId);
        return ResponseEntity.ok().body(new ApiResponseDto("Module published successfully"));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDto> getAllModulesOfUser() {

        List<ModulePreviewDto> modules = moduleService.getAllModulesPreview();
        return ResponseEntity.ok().body(new ApiResponseDto("Modules retrieved successfully", modules));
    }

    @GetMapping("/created-by-me")
    public ResponseEntity<ApiResponseDto> getModulesCreatedByMe() {

        List<ModulePreviewDto> modules = moduleService.getModulesCreatedByMe();
        return ResponseEntity.ok().body(new ApiResponseDto("Modules created by me retrieved successfully", modules));
    }

    @DeleteMapping("/{moduleId}")
    public ResponseEntity<ApiResponseDto> deleteModule(@PathVariable String moduleId) {

        moduleService.deleteModule(moduleId);
        return ResponseEntity.ok().body(new ApiResponseDto("Module deleted successfully"));
    }
}
