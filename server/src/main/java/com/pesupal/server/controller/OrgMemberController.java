package com.pesupal.server.controller;

import com.pesupal.server.dto.request.AddOrgMemberDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.OrgDetailDto;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.helpers.OrgSubscriptionManager;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.service.interfaces.OrgMemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/people")
public class OrgMemberController extends OrgSubscriptionManager {

    private final OrgMemberService orgMemberService;

    @PostMapping("/new_member")
    public ResponseEntity<ApiResponseDto> addMemberToOrg(@RequestBody AddOrgMemberDto addOrgMemberDto) {

        OrgMember orgMember = orgMemberService.addMemberToOrg(addOrgMemberDto, getCurrentUserId(), getCurrentOrgId(), false);
        return ResponseEntity.ok(new ApiResponseDto("Member added to organization successfully.", orgMember));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponseDto> getOrgMemberByUserAndOrg(@PathVariable Long userId) {

        // checkOrgSubscription();

        UserBasicInfoDto userBasicInfoDto = orgMemberService.getOrgMemberBasicInfoByUserIdAndOrgId(userId, getCurrentOrgId());
        return ResponseEntity.ok(new ApiResponseDto("Organization member retrieved successfully.", userBasicInfoDto));
    }

    @GetMapping("/display-picture")
    public ResponseEntity<ApiResponseDto> getOrgMemberImage(@RequestParam(required = false) Long userId) {

        if (userId == null) {
            userId = getCurrentUserId();
        }

        String imageUrl = orgMemberService.getOrgMemberImageByUserIdAndOrgId(userId, getCurrentOrgId());
        return ResponseEntity.ok(new ApiResponseDto("Organization member image retrieved successfully.", imageUrl));
    }

    @GetMapping("/orgs")
    public ResponseEntity<ApiResponseDto> getOrgList() {

        List<OrgDetailDto> orgDetails = orgMemberService.listOfOrgUserPartOf(getCurrentUserId());
        return ResponseEntity.ok(new ApiResponseDto("List of orgs retrieved successfully.", orgDetails));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseDto> getAllOrgMembers() {

        List<UserBasicInfoDto> orgMembers = orgMemberService.getAllOrgMembers(getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok(new ApiResponseDto("List of organization members retrieved successfully.", orgMembers));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponseDto> getMyProfile() {

        UserPreviewDto userPreviewDto = orgMemberService.getUserPreview(getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok().body(new ApiResponseDto("Profile fetched successfully", userPreviewDto));
    }
}
