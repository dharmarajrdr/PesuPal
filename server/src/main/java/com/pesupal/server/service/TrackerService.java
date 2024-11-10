package com.pesupal.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pesupal.server.Util;
import com.pesupal.server.dto.StreakDTO;
import com.pesupal.server.dto.TrackerDTO;
import com.pesupal.server.enums.Days;
import com.pesupal.server.model.Streak;
import com.pesupal.server.model.Tracker;
import com.pesupal.server.repository.StreakRepository;
import com.pesupal.server.repository.TrackerRepository;

@Service
public class TrackerService {

    @Autowired
    private TrackerRepository trackerRepository;

    @Autowired
    private StreakRepository streakRepository;

    private Tracker convertTrackerDTOToEntity(TrackerDTO trackerDTO) {
        Tracker tracker = new Tracker();
        tracker.setUserId(trackerDTO.getUserId());
        tracker.setTitle(trackerDTO.getTitle());
        tracker.setDescription(trackerDTO.getDescription());
        tracker.setCreatedOn(new Date());
        tracker.setFrequency(trackerDTO.getFrequency());
        return tracker;
    }

    private Streak convertStreakDTOToEntity(StreakDTO streakDTO) {
        Streak streak = new Streak();
        streak.setStreakDate(streakDTO.getStreakDate());
        streak.setStreakDay(streakDTO.getStreakDay());
        return streak;
    }

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
        Tracker tracker = convertTrackerDTOToEntity(trackerDTO);

        // Save the entity
        trackerRepository.save(tracker);
    }

    public void saveStreak(Integer user_id, StreakDTO streakDto) {

        // Get streak date
        Date streakDate = streakDto.getStreakDate();

        Integer habitId = streakDto.getHabitId();

        // validate streak owner
        Tracker tracker = trackerRepository.findByUserIdAndHabitId(user_id, habitId);

        if (tracker == null) {
            throw new IllegalArgumentException("Unable to find tracker with habitId: " + habitId);
        }

        // add streak if and only if the date is today
        if (Util.isSameDate(new Date(), streakDate)) {
            try {
                Streak streak = convertStreakDTOToEntity(streakDto);
                streak.setTracker(new Tracker(habitId));    // Assuming the Tracker object only needs the ID
                streakRepository.save(streak);
            } catch (DataIntegrityViolationException e) {
                throw new RuntimeException("Streak already added for this habit on this date", e); // Handle the exception (e.g., duplicate streak)
            }
        } else {
            throw new IllegalArgumentException("You are not allowed to add streak for past/future dates.");
        }
    }

}
