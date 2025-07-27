package com.pesupal.server.controller;

import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.DepartmentDto;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.DepartmentService;
import com.pesupal.server.service.interfaces.OrgMemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/department")
public class DepartmentController extends CurrentValueRetriever {

    private final DepartmentService departmentService;
    private final OrgMemberService orgMemberService;

    @GetMapping("/{departmentId}/members")
    public ResponseEntity<ApiResponseDto> getAllMembers(@PathVariable Long departmentId) {

        List<UserBasicInfoDto> members = orgMemberService.getAllMembers(departmentId, getCurrentOrgMember());
        return ResponseEntity.ok().body(new ApiResponseDto("Members retrieved successfully", members));
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<ApiResponseDto> getDepartment(@PathVariable Long departmentId) {

        DepartmentDto departmentDto = departmentService.getDepartmentByIdAndOrgId(departmentId);
        return ResponseEntity.ok().body(new ApiResponseDto("Members retrieved successfully", departmentDto));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseDto> getDepartment() {

        DepartmentDto departmentDto = departmentService.getUserDepartment();
        return ResponseEntity.ok().body(new ApiResponseDto("Department retrieved successfully", departmentDto));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDto> getAllDepartments() {

        List<DepartmentDto> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok().body(new ApiResponseDto("Departments retrieved successfully", departments));
    }
}
