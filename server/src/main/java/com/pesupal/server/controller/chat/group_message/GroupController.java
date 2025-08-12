package com.pesupal.server.controller.chat.group_message;

import com.pesupal.server.dto.request.chat.group_message.CreateGroupDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.chat.ChatPreviewDto;
import com.pesupal.server.dto.response.chat.RecentChatPagedDto;
import com.pesupal.server.dto.response.chat.group_message.GroupDto;
import com.pesupal.server.service.interfaces.chat.group_message.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createGroupMessage(@RequestBody CreateGroupDto createGroupDto) {

        GroupDto groupDto = groupService.createGroup(createGroupDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Group message created successfully", groupDto));
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<ApiResponseDto> updateGroup(@PathVariable String groupId, @RequestBody CreateGroupDto createGroupDto) {

        GroupDto updatedGroup = groupService.updateGroup(groupId, createGroupDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Group updated successfully", updatedGroup));
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<ApiResponseDto> deleteGroup(@PathVariable String groupId) {

        groupService.deleteGroup(groupId);
        return ResponseEntity.ok().body(new ApiResponseDto("Group deleted successfully"));
    }

    @GetMapping("/recent")
    public ResponseEntity<ApiResponseDto> getAllGroups(@RequestParam Integer page, @RequestParam Integer size) {

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        RecentChatPagedDto recentChatPagedDto = groupService.getAllGroups(pageable);
        return ResponseEntity.ok().body(new ApiResponseDto("Groups retrieved successfully", recentChatPagedDto.getChats(), recentChatPagedDto.getPageable()));
    }

    @GetMapping("/preview/{groupId}")
    public ResponseEntity<ApiResponseDto> getGroupChatPreview(@PathVariable String groupId) {

        ChatPreviewDto chatPreviewDto = groupService.getGroupChatPreviewByChatId(groupId);
        return ResponseEntity.ok(new ApiResponseDto("Group chat preview retrieved successfully", chatPreviewDto));
    }

    @PutMapping("/reopen/{groupId}")
    public ResponseEntity<ApiResponseDto> reopenGroup(@PathVariable String groupId) {

        groupService.reopenGroup(groupId);
        return ResponseEntity.ok(new ApiResponseDto("Group reopened successfully"));
    }
}
