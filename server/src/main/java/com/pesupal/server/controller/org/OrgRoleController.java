package com.pesupal.server.controller.org;

import com.pesupal.server.dto.request.org.CreateRoleDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.org.OrgRoleDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.org.OrgRoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/org-role")
public class OrgRoleController extends CurrentValueRetriever {

    private final OrgRoleService orgRoleService;

    @PostMapping("")
    public ResponseEntity<ApiResponseDto> createRole(@RequestBody CreateRoleDto createRoleDto) {

        OrgRoleDto orgRole = orgRoleService.createOrgRole(createRoleDto, getCurrentOrgMember());
        return ResponseEntity.ok().body(new ApiResponseDto("Role created successfully", orgRole));
    }

    @PatchMapping("/{roleId}")
    public ResponseEntity<ApiResponseDto> updateRole(@PathVariable Long roleId, @RequestBody CreateRoleDto updateRoleDto) {

        OrgRoleDto orgRole = orgRoleService.updateOrgRole(roleId, updateRoleDto, getCurrentOrgMember());
        return ResponseEntity.ok().body(new ApiResponseDto("Role updated successfully", orgRole));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseDto> getAllRoles() {

        List<OrgRoleDto> roles = orgRoleService.getAllRoles(getCurrentOrgMember());
        return ResponseEntity.ok().body(new ApiResponseDto("Roles fetched successfully", roles));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<ApiResponseDto> deleteRole(@PathVariable Long roleId) {

        orgRoleService.deleteRoleById(roleId, getCurrentOrgMember());
        return ResponseEntity.ok().body(new ApiResponseDto("Role deleted successfully."));
    }
}
