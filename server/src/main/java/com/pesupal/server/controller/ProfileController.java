package com.pesupal.server.controller;

import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.service.interfaces.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponseDto> getOrgMemberByUserAndOrg(@PathVariable String userId) {

        UserBasicInfoDto userBasicInfoDto = profileService.getOrgMemberBasicInfoByUserIdAndOrgId(userId);
        return ResponseEntity.ok(new ApiResponseDto("Organization member retrieved successfully.", userBasicInfoDto));
    }
}
