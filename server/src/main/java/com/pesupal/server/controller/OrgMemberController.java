package com.pesupal.server.controller;

import com.pesupal.server.dto.request.AddOrgMemberDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.service.interfaces.OrgMemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/org-member")
public class OrgMemberController {

    private final OrgMemberService orgMemberService;

    @PostMapping("/add_member")
    public ResponseEntity<ApiResponseDto> addMemberToOrg(@RequestBody AddOrgMemberDto addOrgMemberDto, @RequestParam Long userId) {

        OrgMember orgMember = orgMemberService.addMemberToOrg(addOrgMemberDto, userId, false);
        return ResponseEntity.ok(new ApiResponseDto("Member added to organization successfully.", orgMember));
    }

    @GetMapping("/{orgId}/user/{userId}")
    public ResponseEntity<ApiResponseDto> getOrgMemberByUserAndOrg(@PathVariable Long userId, @PathVariable Long orgId) {

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);
        return ResponseEntity.ok(new ApiResponseDto("Organization member retrieved successfully.", orgMember));
    }
}
