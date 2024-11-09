package com.pesupal.server.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pesupal.server.enums.Days;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Tracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer habitId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private Date createdOn;

    @Column(nullable = false)
    private String frequency;

    @OneToMany(mappedBy = "tracker")
    @JsonManagedReference
    private List<Streak> streaks;

    public void setFrequency(ArrayList<Days> days) {
        this.frequency = days.stream()
                .map(Days::name) // Converts enum to String using its name
                .collect(Collectors.joining(","));
    }

    public List<Days> getFrequency() {
        return List.of(frequency.split(",")).stream()
                .map(Days::valueOf) // Converts String to enum using its valueOf method
                .collect(Collectors.toList());
    }
}
