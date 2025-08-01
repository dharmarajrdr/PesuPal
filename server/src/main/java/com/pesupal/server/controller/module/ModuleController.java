package com.pesupal.server.controller.module;

import com.pesupal.server.dto.request.module.CreateModuleDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.service.interfaces.module.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/module")
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createModule(@RequestBody CreateModuleDto createModuleDto) {

        Module module = moduleService.createModule(createModuleDto);
        ApiResponseDto response = new ApiResponseDto("Module created successfully", module);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{moduleId}/publish")
    public ResponseEntity<ApiResponseDto> publishModule(@PathVariable String moduleId) {

        moduleService.publishModule(moduleId);
        return ResponseEntity.ok().body(new ApiResponseDto("Module published successfully"));
    }
}
