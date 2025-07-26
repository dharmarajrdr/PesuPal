package com.pesupal.server.controller;

import com.pesupal.server.dto.request.CreateOrgDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.org.Org;
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
public class OrgController extends CurrentValueRetriever {

    private final OrgService orgService;

    @PostMapping()
    public ResponseEntity<ApiResponseDto> createOrg(@RequestBody CreateOrgDto createOrgDto) {

        Org createdOrg = orgService.createOrg(createOrgDto, getCurrentUserPublicId());
        return ResponseEntity.ok(new ApiResponseDto("Organization created successfully.", createdOrg));
    }
}
