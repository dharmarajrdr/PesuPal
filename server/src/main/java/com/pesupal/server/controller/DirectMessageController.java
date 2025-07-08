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

    @GetMapping("/{chatId}")
    public ResponseEntity<ApiResponseDto> getDirectMessagesByUserId(@PathVariable String chatId, @RequestParam Integer page, @RequestParam Integer size) {

        GetConversationBetweenUsers getConversationBetweenUsers = new GetConversationBetweenUsers(chatId, page, size);
        List<DirectMessageResponseDto> directMessageResponseDtos = directMessageService.getDirectMessagesBetweenUsers(getConversationBetweenUsers);
        return ResponseEntity.ok(new ApiResponseDto("Direct messages retrieved successfully", directMessageResponseDtos));
    }
}
