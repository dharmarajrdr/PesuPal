package com.pesupal.server.controller.org;

import com.pesupal.server.config.RequestContext;
import com.pesupal.server.dto.request.org.AddOrgMemberDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.org.OrgDetailDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.helpers.OrgSubscriptionManager;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.org.OrgSubscriptionHistory;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.security.CustomUserDetails;
import com.pesupal.server.service.interfaces.org.OrgMemberService;
import com.pesupal.server.service.interfaces.org.OrgSubscriptionHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/people")
public class OrgMemberController extends OrgSubscriptionManager {

    private final OrgMemberService orgMemberService;
    private final OrgSubscriptionHistoryService orgSubscriptionHistoryService;

    @PostMapping("/new_member")
    public ResponseEntity<ApiResponseDto> addMemberToOrg(@RequestBody AddOrgMemberDto addOrgMemberDto) {

        OrgMember orgMember = orgMemberService.addMemberToOrg(addOrgMemberDto, getCurrentOrgMember(), false);
        return ResponseEntity.ok(new ApiResponseDto("Member added to organization successfully.", orgMember));
    }

    @PatchMapping("/{orgMemberPublicId}")
    public ResponseEntity<ApiResponseDto> updateOrgMember(@PathVariable String orgMemberPublicId, @RequestBody AddOrgMemberDto addOrgMemberDto) {

        orgMemberService.updateOrgMember(orgMemberPublicId, addOrgMemberDto, getCurrentOrgMember());
        return ResponseEntity.ok(new ApiResponseDto("Organization member updated successfully."));
    }

    @GetMapping("/orgs")
    public ResponseEntity<ApiResponseDto> getOrgList() {

        List<OrgDetailDto> orgDetails = orgMemberService.listOfOrgUserPartOf(getCurrentUserId());
        return ResponseEntity.ok(new ApiResponseDto("List of orgs retrieved successfully.", orgDetails));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseDto> getAllOrgMembers() {

        List<UserBasicInfoDto> orgMembers = orgMemberService.getAllOrgMembers(getCurrentOrgMember());
        return ResponseEntity.ok(new ApiResponseDto("List of organization members retrieved successfully.", orgMembers));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponseDto> getSearchedOrgMembers(@RequestParam(name = "query", defaultValue = "") String search,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size,
                                                                @RequestParam(defaultValue = "displayName") String sortBy,
                                                                @RequestParam(defaultValue = "ASC") String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        List<UserBasicInfoDto> orgMembers = orgMemberService.getSearchedOrgMembers(getCurrentOrgMember(), search, pageable);
        return ResponseEntity.ok(new ApiResponseDto("List of organization members retrieved successfully.", orgMembers));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponseDto> getMyProfile() {

        UserPreviewDto userPreviewDto = orgMemberService.getUserPreview(getCurrentOrgMember());
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
        String userId = customUserDetails.getUserPublicId();
        String orgMemberId = customUserDetails.getOrgMemberPublicId();
        Map<String, String> userDetail = new HashMap<>();
        userDetail.put("userId", userId);
        if (orgMemberId != null) {
            OrgMember orgMember = orgMemberService.getOrgMemberByPublicId(orgMemberId);
            Org org = orgMember.getOrg();
            OrgSubscriptionHistory orgSubscriptionHistory = orgSubscriptionHistoryService.getLatestSubscription(org.getId()).orElseThrow(() -> new DataNotFoundException("No subscription history found for org with ID " + org.getId()));
            userDetail.put("orgId", org.getPublicId());
            userDetail.put("orgMemberId", orgMemberId);
            userDetail.put("orgStatus", orgSubscriptionHistory.getEndDate().isBefore(LocalDateTime.now()) ? "Inactive" : "Active");
        }
        return ResponseEntity.ok().body(new ApiResponseDto("Token decoded successfully", userDetail));
    }
}
