package com.pesupal.server.controller.group;

import com.pesupal.server.dto.request.group.CreateGroupDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.group.GroupDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.group.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupController extends CurrentValueRetriever {

    private final GroupService groupService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createGroupMessage(@RequestBody CreateGroupDto createGroupDto) {

        GroupDto groupDto = groupService.createGroup(createGroupDto, getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok().body(new ApiResponseDto("Group message created successfully", groupDto));
    }
}
