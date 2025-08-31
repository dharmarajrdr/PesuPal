package com.pesupal.server.controller.org;

import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.service.interfaces.org.PresenceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/presence")
public class PresenceController {

    private final PresenceService presenceService;

    @PatchMapping("/inform")
    public ResponseEntity<ApiResponseDto> informPresence() {

        presenceService.informPresence();
        return ResponseEntity.ok().body(new ApiResponseDto("Presence informed successfully"));
    }
}
