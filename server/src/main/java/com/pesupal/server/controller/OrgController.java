package com.pesupal.server.controller;

import com.pesupal.server.dto.request.CreateOrgDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.security.SecurityUtil;
import com.pesupal.server.service.interfaces.OrgService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/org")
public class OrgController {

    private final OrgService orgService;
    private final SecurityUtil securityUtil;

    @PostMapping()
    public ResponseEntity<ApiResponseDto> createOrg(@RequestBody CreateOrgDto createOrgDto) {

        Long userId = securityUtil.getCurrentUserId();
        Org createdOrg = orgService.createOrg(createOrgDto, userId);
        return ResponseEntity.ok(new ApiResponseDto("Organization created successfully.", createdOrg));
    }
}
