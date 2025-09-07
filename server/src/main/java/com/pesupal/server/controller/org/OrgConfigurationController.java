package com.pesupal.server.controller.org;

import com.pesupal.server.dto.request.org.OrgConfigurationDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.org.OrgConfigurationService;
import com.pesupal.server.service.interfaces.org.OrgRoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/org-configuration")
public class OrgConfigurationController extends CurrentValueRetriever {

    private final OrgRoleService orgRoleService;
    private final OrgConfigurationService orgConfigurationService;

    @PostMapping("")
    public ResponseEntity<ApiResponseDto> createConfiguration(@RequestBody OrgConfigurationDto createOrgConfigurationDto) {

        orgConfigurationService.createConfiguration(createOrgConfigurationDto, getCurrentOrgMember());
        return ResponseEntity.ok().body(new ApiResponseDto("Action permitted successfully."));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseDto> getOrgConfiguration() {

        Map<String, Object> configurations = Map.ofEntries(
                Map.entry("permissions", orgConfigurationService.getPermittedActionsInOrg(getCurrentOrgMember())),
                Map.entry("roles", orgRoleService.getAllRoles(getCurrentOrgMember()))
        );
        return ResponseEntity.ok().body(new ApiResponseDto("Org configurations fetched successfully.", configurations));
    }

    @DeleteMapping("")
    public ResponseEntity<ApiResponseDto> revokePermission(@RequestBody OrgConfigurationDto deleteOrgConfigurationDto) {

        orgConfigurationService.removeConfiguration(deleteOrgConfigurationDto, getCurrentOrgMember());
        return ResponseEntity.ok().body(new ApiResponseDto("Action revoked successfully."));
    }
}
