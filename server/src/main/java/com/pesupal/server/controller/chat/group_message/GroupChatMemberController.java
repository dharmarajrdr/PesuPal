package com.pesupal.server.controller.chat.group_message;

import com.pesupal.server.dto.request.chat.group_message.AddGroupMemberDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.chat.group_message.GroupDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.service.interfaces.chat.group_message.GroupChatMemberService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    @DeleteMapping("/leave/{groupId}")
    public ResponseEntity<ApiResponseDto> leaveGroup(@PathVariable String groupId) {

        groupChatMemberService.leaveGroup(groupId);
        return ResponseEntity.ok().body(new ApiResponseDto("Left group successfully"));
    }

    @DeleteMapping("/remove-member")
    public ResponseEntity<ApiResponseDto> removeMemberFromGroup(@RequestBody AddGroupMemberDto removeGroupMemberDto) {

        groupChatMemberService.removeMemberFromGroup(removeGroupMemberDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Member removed successfully"));
    }

    @PostMapping("/add-member")
    public ResponseEntity<ApiResponseDto> addMemberToGroup(@RequestBody AddGroupMemberDto addGroupMemberDto) {

        UserPreviewDto userPreviewDto = groupChatMemberService.addMemberToGroup(addGroupMemberDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Member added successfully", userPreviewDto));
    }

    @GetMapping("/non-participants/{groupId}")
    public ResponseEntity<ApiResponseDto> getNonParticipantMembers(@PathVariable String groupId,
                                                                   @RequestParam(required = false) String search,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "25") int size) {

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        List<UserPreviewDto> nonParticipantMembers = groupChatMemberService.getNonParticipantMembers(groupId, search, pageable);
        return ResponseEntity.ok().body(new ApiResponseDto("Non-participant members retrieved successfully", nonParticipantMembers));
    }

    @GetMapping("/members/{groupId}")
    public ResponseEntity<ApiResponseDto> getGroupMembers(@PathVariable String groupId) {

        Map<Role, List<UserPreviewDto>> roleListMap = groupChatMemberService.getGroupMembers(groupId);
        return ResponseEntity.ok().body(new ApiResponseDto("Group members retrieved successfully", roleListMap));
    }
}
