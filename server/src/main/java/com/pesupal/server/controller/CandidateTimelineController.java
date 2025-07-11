package com.pesupal.server.controller;

import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.CandidateTimelineService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/candidate-timeline")
public class CandidateTimelineController extends CurrentValueRetriever {

    private final CandidateTimelineService candidateTimelineService;
}
