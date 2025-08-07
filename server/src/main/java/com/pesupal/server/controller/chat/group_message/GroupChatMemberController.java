package com.pesupal.server.controller.chat.group_message;

import com.pesupal.server.dto.request.chat.group_message.AddGroupMemberDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.chat.group_message.GroupDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.service.interfaces.chat.group_message.GroupChatMemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/group-chat-member")
public class GroupChatMemberController {

    private final GroupChatMemberService groupChatMemberService;

    @PostMapping("/join/{groupId}")
    public ResponseEntity<ApiResponseDto> joinGroup(@PathVariable String groupId) {

        GroupDto groupDto = groupChatMemberService.joinGroup(groupId);
        return ResponseEntity.ok().body(new ApiResponseDto("Joined group successfully", groupDto));
    }

    @PostMapping("/add-member")
    public ResponseEntity<ApiResponseDto> addMemberToGroup(@RequestBody AddGroupMemberDto addGroupMemberDto) {

        UserPreviewDto userPreviewDto = groupChatMemberService.addMemberToGroup(addGroupMemberDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Member added successfully", userPreviewDto));
    }

    @GetMapping("/members/{groupId}")
    public ResponseEntity<ApiResponseDto> getGroupMembers(@PathVariable String groupId) {

        Map<Role, List<UserPreviewDto>> roleListMap = groupChatMemberService.getGroupMembers(groupId);
        return ResponseEntity.ok().body(new ApiResponseDto("Group members retrieved successfully", roleListMap));
    }
}
