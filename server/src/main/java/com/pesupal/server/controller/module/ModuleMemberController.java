package com.pesupal.server.controller.module;

import com.pesupal.server.dto.request.module.AddModuleMemberDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.service.interfaces.module.ModuleMemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
