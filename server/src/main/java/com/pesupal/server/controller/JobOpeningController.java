package com.pesupal.server.controller;

import com.pesupal.server.service.interfaces.JobOpeningService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/job-opening")
public class JobOpeningController {

    private final JobOpeningService jobOpeningService;
}
