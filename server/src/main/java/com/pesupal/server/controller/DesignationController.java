package com.pesupal.server.controller;

import com.pesupal.server.dto.request.CreateDesignationDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.model.user.Designation;
import com.pesupal.server.service.interfaces.DesignationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/designation")
public class DesignationController {

    private final DesignationService designationService;

    @PostMapping("")
    public ResponseEntity<ApiResponseDto> createDesignation(@RequestBody CreateDesignationDto createDesignationDto) {

        Designation designation = designationService.createDesignation(createDesignationDto);
        return ResponseEntity.ok(new ApiResponseDto("Designation created successfully.", designation));
    }
}
