package com.pesupal.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pesupal.server.dto.TrackerDTO;
import com.pesupal.server.model.Tracker;
import com.pesupal.server.service.TrackerService;

@RestController
@RequestMapping("/api")
public class TrackerController {

    @Autowired
    private TrackerService trackerService;

    @GetMapping("/tracker/{id}")
    public List<Tracker> getTrackers(@PathVariable Integer id) {
        return trackerService.findAllByUserId(id);
    }

    @PostMapping("/tracker/{id}")
    public String setTracker(@PathVariable Integer id, @RequestBody TrackerDTO tracker) {
        tracker.setUserId(id);
        trackerService.save(tracker);
        return "Tracker added";
    }
}
