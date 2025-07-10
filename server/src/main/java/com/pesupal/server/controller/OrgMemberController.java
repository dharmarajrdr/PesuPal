package com.pesupal.server.controller;

import com.pesupal.server.dto.request.AddOrgMemberDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.OrgDetailDto;
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

        checkOrgSubscription();

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, getCurrentOrgId());
        return ResponseEntity.ok(new ApiResponseDto("Organization member retrieved successfully.", orgMember));
    }

    @GetMapping("/orgs")
    public ResponseEntity<ApiResponseDto> getOrgList() {

        List<OrgDetailDto> orgDetails = orgMemberService.listOfOrgUserPartOf(getCurrentUserId());
        return ResponseEntity.ok(new ApiResponseDto("List of orgs retrieved successfully.", orgDetails));
    }
}
