package com.pesupal.server.controller.org;

import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.org.OrgMemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/presence")
public class PresenceController extends CurrentValueRetriever {

    private final OrgMemberService orgMemberService;

    @PatchMapping("/inform")
    public ResponseEntity<ApiResponseDto> informPresence() {

        orgMemberService.informPresence(getCurrentOrgMember());
        return ResponseEntity.ok().body(new ApiResponseDto("Presence informed successfully"));
    }
}
