package com.pesupal.server.controller;

import com.pesupal.server.config.RequestContext;
import com.pesupal.server.dto.request.AddOrgMemberDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.OrgDetailDto;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.helpers.OrgSubscriptionManager;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.security.CustomUserDetails;
import com.pesupal.server.service.interfaces.OrgMemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/people")
public class OrgMemberController extends OrgSubscriptionManager {

    private final OrgMemberService orgMemberService;

    @PostMapping("/new_member")
    public ResponseEntity<ApiResponseDto> addMemberToOrg(@RequestBody AddOrgMemberDto addOrgMemberDto) {

        OrgMember orgMember = orgMemberService.addMemberToOrg(addOrgMemberDto, false);
        return ResponseEntity.ok(new ApiResponseDto("Member added to organization successfully.", orgMember));
    }

    @GetMapping("/{orgMemberPublicId}")
    public ResponseEntity<ApiResponseDto> getOrgMemberByUserAndOrg(@PathVariable String orgMemberPublicId) {

        UserBasicInfoDto userBasicInfoDto = orgMemberService.getUserBasicInfoByOrgMemberPublicId(orgMemberPublicId);
        return ResponseEntity.ok(new ApiResponseDto("Organization member retrieved successfully.", userBasicInfoDto));
    }

    @GetMapping("/display-picture")
    public ResponseEntity<ApiResponseDto> getOrgMemberImage(@RequestParam(name = "userId") String orgMemberPublicId) {

        if (orgMemberPublicId == null) {
            orgMemberPublicId = getCurrentOrgMemberPublicId();
        }

        String imageUrl = orgMemberService.getImageByOrgMemberPublicId(orgMemberPublicId);
        return ResponseEntity.ok(new ApiResponseDto("Organization member image retrieved successfully.", imageUrl));
    }

    @GetMapping("/orgs")
    public ResponseEntity<ApiResponseDto> getOrgList() {

        List<OrgDetailDto> orgDetails = orgMemberService.listOfOrgUserPartOf();
        return ResponseEntity.ok(new ApiResponseDto("List of orgs retrieved successfully.", orgDetails));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseDto> getAllOrgMembers() {

        List<UserBasicInfoDto> orgMembers = orgMemberService.getAllOrgMembers();
        return ResponseEntity.ok(new ApiResponseDto("List of organization members retrieved successfully.", orgMembers));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponseDto> getMyProfile() {

        UserPreviewDto userPreviewDto = orgMemberService.getCurrentOrgMemberPreview();
        return ResponseEntity.ok().body(new ApiResponseDto("Profile fetched successfully", userPreviewDto));
    }

    @GetMapping("/token")
    public ResponseEntity<ApiResponseDto> generateTokenWithOrgMemberId() {

        CustomUserDetails customUserDetails = getCurrentUserDetails();
        String publicOrgId = RequestContext.get("X-ORG-ID", String.class);

        String token = orgMemberService.generateTokenWithOrgMemberId(customUserDetails.getUserPublicId(), publicOrgId);
        return ResponseEntity.ok().body(new ApiResponseDto("Token generated successfully", token));
    }

    @GetMapping("/who-am-i")
    public ResponseEntity<ApiResponseDto> whoAmI() {

        CustomUserDetails customUserDetails = getCurrentUserDetails();
        Map<String, String> userDetail = new HashMap<>();
        userDetail.put("userId", customUserDetails.getUserPublicId());
        userDetail.put("orgMemberId", customUserDetails.getOrgMemberPublicId());
        return ResponseEntity.ok().body(new ApiResponseDto("Token decoded successfully", userDetail));
    }
}
