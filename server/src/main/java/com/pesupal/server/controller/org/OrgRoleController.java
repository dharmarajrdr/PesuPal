package com.pesupal.server.controller.org;

import com.pesupal.server.dto.request.org.CreateRoleDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.org.OrgRoleDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.org.OrgRoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/org-role")
public class OrgRoleController extends CurrentValueRetriever {

    private final OrgRoleService orgRoleService;

    @PostMapping("")
    public ResponseEntity<ApiResponseDto> createRole(@RequestBody CreateRoleDto createRoleDto) {

        OrgRoleDto orgRole = orgRoleService.createOrgRole(createRoleDto.getName(), getCurrentOrgMember());
        return ResponseEntity.ok().body(new ApiResponseDto("Role created successfully", orgRole));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<ApiResponseDto> deleteRole(@PathVariable Long roleId) {

        orgRoleService.deleteRoleById(roleId, getCurrentOrgMember());
        return ResponseEntity.ok().body(new ApiResponseDto("Role deleted successfully."));
    }
}
