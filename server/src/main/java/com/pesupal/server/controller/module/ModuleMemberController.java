package com.pesupal.server.controller.module;

import com.pesupal.server.dto.request.module.AddModuleMemberDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.service.interfaces.module.ModuleMemberService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/module")
public class ModuleMemberController {

    private final ModuleMemberService moduleMemberService;

    @PostMapping("/{moduleId}/member")
    public ResponseEntity<ApiResponseDto> addMemberToModule(@PathVariable String moduleId, @RequestBody AddModuleMemberDto addModuleMemberDto) {

        addModuleMemberDto.setModuleId(moduleId);
        moduleMemberService.addMemberToModule(moduleId, addModuleMemberDto);
        return ResponseEntity.ok(new ApiResponseDto("Member added to module successfully"));
    }
    
    @GetMapping("/{moduleId}/non-members")
    public ResponseEntity<ApiResponseDto> getNonMembersOfModule(@PathVariable String moduleId,
                                                                @RequestParam(required = false) String search,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "25") int size) {

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        List<UserPreviewDto> nonParticipantMembers = moduleMemberService.getNonMembersOfModule(moduleId, search, pageable);
        return ResponseEntity.ok(new ApiResponseDto("Non-member users retrieved successfully", nonParticipantMembers));
    }
}
