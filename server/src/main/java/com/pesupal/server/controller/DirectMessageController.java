package com.pesupal.server.controller;

import com.pesupal.server.dto.request.GetConversationBetweenUsers;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.DirectMessageResponseDto;
import com.pesupal.server.service.interfaces.DirectMessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/direct-messages")
public class DirectMessageController {

    private final DirectMessageService directMessageService;

    @GetMapping("/{orgId}/users/{userId1}/{userId2}")
    public ResponseEntity<ApiResponseDto> getDirectMessagesByUserId(@PathVariable Long userId1, @PathVariable Long userId2, @PathVariable Long orgId, @RequestParam Integer page, @RequestParam Integer size) {

        GetConversationBetweenUsers getConversationBetweenUsers = new GetConversationBetweenUsers(userId1, userId2, orgId, page, size);
        List<DirectMessageResponseDto> directMessageResponseDtos = directMessageService.getDirectMessagesBetweenUsers(getConversationBetweenUsers);
        return ResponseEntity.ok(new ApiResponseDto("Direct messages retrieved successfully", directMessageResponseDtos));
    }
}
