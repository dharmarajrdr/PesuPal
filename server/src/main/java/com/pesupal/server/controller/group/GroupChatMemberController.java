package com.pesupal.server.controller.group;

import com.pesupal.server.dto.request.group.AddGroupMemberDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.group.GroupDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.group.GroupChatMemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/group-chat-member")
public class GroupChatMemberController extends CurrentValueRetriever {

    private final GroupChatMemberService groupChatMemberService;

    @PostMapping("/join/{groupId}")
    public ResponseEntity<ApiResponseDto> joinGroup(@PathVariable Long groupId) {

        GroupDto groupDto = groupChatMemberService.joinGroup(groupId);
        return ResponseEntity.ok().body(new ApiResponseDto("Group joined successfully", groupDto));
    }

    @PostMapping("/add-member")
    public ResponseEntity<ApiResponseDto> addMemberToGroup(@RequestBody AddGroupMemberDto addGroupMemberDto) {

        UserPreviewDto userPreviewDto = groupChatMemberService.addMemberToGroup(addGroupMemberDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Member added successfully", userPreviewDto));
    }

    @GetMapping("/members/{groupId}")
    public ResponseEntity<ApiResponseDto> getGroupMembers(@PathVariable Long groupId) {

        Map<Role, List<UserPreviewDto>> roleListMap = groupChatMemberService.getGroupMembers(groupId);
        return ResponseEntity.ok().body(new ApiResponseDto("Group members retrieved successfully", roleListMap));
    }
}
