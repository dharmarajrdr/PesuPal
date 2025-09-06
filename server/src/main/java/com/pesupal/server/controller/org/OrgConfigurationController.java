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

    private final OrgConfigurationService orgConfigurationService;
    private final OrgRoleService orgRoleService;

    @PostMapping("")
    public ResponseEntity<ApiResponseDto> createConfiguration(@RequestBody OrgConfigurationDto createOrgConfigurationDto) {

        orgConfigurationService.createConfiguration(createOrgConfigurationDto, getCurrentOrgMember());
        return ResponseEntity.ok().body(new ApiResponseDto("Action '" + createOrgConfigurationDto.getAction().name() + "' configured."));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseDto> getOrgConfiguration() {

        Map configurations = Map.ofEntries(
                Map.entry("permissions", orgConfigurationService.getPermittedActionsInOrg(getCurrentOrgMember())),
                Map.entry("roles", orgRoleService.getAllRoles(getCurrentOrgMember()))
        );
        return ResponseEntity.ok().body(new ApiResponseDto("Org configurations fetched successfully.", configurations));
    }

    @DeleteMapping("")
    public ResponseEntity<ApiResponseDto> revokePermission(@RequestBody OrgConfigurationDto deleteOrgConfigurationDto) {

        orgConfigurationService.removeConfiguration(deleteOrgConfigurationDto, getCurrentOrgMember());
        return ResponseEntity.ok().body(new ApiResponseDto("Action '" + deleteOrgConfigurationDto.getAction().name() + "' configuration removed."));
    }
}
