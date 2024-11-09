package com.pesupal.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pesupal.server.dto.TrackerDTO;
import com.pesupal.server.enums.Days;
import com.pesupal.server.model.Tracker;
import com.pesupal.server.repository.TrackerRepository;

@Service
public class TrackerService {

    @Autowired
    private TrackerRepository trackerRepository;

    public List<Tracker> findAllByUserId(Integer id) {

        Pageable pageable = PageRequest.of(0, 10);

        List<Tracker> trackers = trackerRepository.findAllByUserId(id, pageable);

        trackers.forEach(tracker -> {
            tracker.setFrequency((ArrayList<Days>) tracker.getFrequency());
        });

        return trackers;
    }

    public void save(TrackerDTO trackerDTO) {

        // Convert DTO to entity
        Tracker tracker = new Tracker();
        tracker.setUserId(trackerDTO.getUserId());
        tracker.setTitle(trackerDTO.getTitle());
        tracker.setDescription(trackerDTO.getDescription());
        tracker.setCreatedOn(new Date()); // Set created date
        tracker.setFrequency(trackerDTO.getFrequency());

        // Save the entity
        trackerRepository.save(tracker);
    }

}
