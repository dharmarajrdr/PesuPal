package com.pesupal.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pesupal.server.dto.StreakDTO;
import com.pesupal.server.dto.TrackerDTO;
import com.pesupal.server.model.Tracker;
import com.pesupal.server.service.TrackerService;

@RestController
@RequestMapping("/api")
public class TrackerController {

    @Autowired
    private TrackerService trackerService;

    @GetMapping("/tracker/{user_id}")
    public List<Tracker> getTrackers(@PathVariable Integer user_id) {
        return trackerService.findAllByUserId(user_id);
    }

    @PostMapping("/tracker/{user_id}")
    public String setTracker(@PathVariable Integer user_id, @RequestBody TrackerDTO tracker) {
        tracker.setUserId(user_id);
        trackerService.save(tracker);
        return "Tracker added";
    }

    @PostMapping("/tracker/{user_id}/streak")
    public String setStreak(@PathVariable Integer user_id, @RequestBody StreakDTO streak) {
        trackerService.saveStreak(user_id, streak);
        return "Streak added";
    }
}
