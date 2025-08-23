package com.pesupal.server.controller.module;

import com.pesupal.server.dto.request.module.UpdateModulePermissionDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.module.ModulePermissionDto;
import com.pesupal.server.service.interfaces.module.ModulePermissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/module")
public class ModulePermissionController {

    private final ModulePermissionService modulePermissionService;

    @GetMapping("/{moduleId}/permissions")
    public ResponseEntity<ApiResponseDto> getModulePermissions(@PathVariable String moduleId) {

        List<ModulePermissionDto> modulePermissions = modulePermissionService.getModulePermissions(moduleId);
        return ResponseEntity.ok(new ApiResponseDto("Module permissions fetched successfully", modulePermissions));
    }

    @PatchMapping("/permissions")
    public ResponseEntity<ApiResponseDto> updateModulePermissions(@RequestBody UpdateModulePermissionDto updateModulePermissionDto) throws IllegalAccessException {

        modulePermissionService.updateModulePermissions(updateModulePermissionDto);
        return ResponseEntity.ok(new ApiResponseDto("Module permissions updated successfully"));
    }
}
