package com.pesupal.server.controller.group;

import com.pesupal.server.dto.request.group.CreateGroupDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.ChatPreviewDto;
import com.pesupal.server.dto.response.RecentChatPagedDto;
import com.pesupal.server.dto.response.group.GroupDto;
import com.pesupal.server.service.interfaces.group.GroupService;
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
    public ResponseEntity<ApiResponseDto> getgroupChatPreview(@PathVariable String groupId) {

        ChatPreviewDto chatPreviewDto = groupService.getGroupChatPreviewByChatId(groupId);
        return ResponseEntity.ok(new ApiResponseDto("Group chat preview retrieved successfully", chatPreviewDto));
    }
}
