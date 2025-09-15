package com.pesupal.server.controller.org;

import com.pesupal.server.dto.request.org.CreateDepartmentDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.dto.response.org.DepartmentDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.department.Department;
import com.pesupal.server.service.interfaces.org.DepartmentService;
import com.pesupal.server.service.interfaces.org.OrgMemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/department")
public class DepartmentController extends CurrentValueRetriever {

    private final DepartmentService departmentService;
    private final OrgMemberService orgMemberService;

    @GetMapping("/{departmentId}/members")
    public ResponseEntity<ApiResponseDto> getAllMembers(@PathVariable String departmentId) {

        List<UserBasicInfoDto> members = departmentService.getAllMembers(departmentId);
        return ResponseEntity.ok().body(new ApiResponseDto("Members retrieved successfully", members));
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<ApiResponseDto> getDepartment(@PathVariable String departmentId) {

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

    @PostMapping("")
    public ResponseEntity<ApiResponseDto> createDepartment(@RequestBody CreateDepartmentDto createDepartmentDto) {

        Department department = departmentService.createDepartment(createDepartmentDto);
        DepartmentDto departmentDto = DepartmentDto.fromDepartment(department);
        return ResponseEntity.ok().body(new ApiResponseDto("Department created successfully", departmentDto));
    }
}
