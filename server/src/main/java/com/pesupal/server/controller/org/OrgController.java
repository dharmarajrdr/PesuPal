package com.pesupal.server.controller.org;

import com.pesupal.server.dto.request.org.CreateOrgDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.org.OrgCreatedDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.org.OrgService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/org")
public class OrgController extends CurrentValueRetriever {

    private final OrgService orgService;

    @PostMapping()
    public ResponseEntity<ApiResponseDto> createOrg(@RequestBody CreateOrgDto createOrgDto) {

        OrgCreatedDto createdOrg = orgService.createOrg(createOrgDto, getCurrentUserPublicId());
        return ResponseEntity.ok(new ApiResponseDto("Organization created successfully.", createdOrg));
    }

    @PatchMapping("/{orgPublicId}")
    public ResponseEntity<ApiResponseDto> updateOrg(@PathVariable String orgPublicId, @RequestBody CreateOrgDto createOrgDto) {

        orgService.updateOrg(orgPublicId, createOrgDto, getCurrentOrgMember());
        return ResponseEntity.ok(new ApiResponseDto("Organization updated successfully."));
    }

    @DeleteMapping("/{orgPublicId}")
    public ResponseEntity<ApiResponseDto> deleteOrg(@PathVariable String orgPublicId) {

        orgService.deleteOrg(orgPublicId, getCurrentOrgMember());
        return ResponseEntity.ok(new ApiResponseDto("Organization deleted successfully."));
    }
}
