package com.pesupal.server.controller.module;

import com.pesupal.server.dto.request.module.CreateModuleDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.service.interfaces.module.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
