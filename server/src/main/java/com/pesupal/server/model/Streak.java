package com.pesupal.server.model;

import java.util.Date;

import com.pesupal.server.enums.Days;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Streak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int streakId;

    @Column(nullable = false)
    private Date streakDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Days streakDay;

    @ManyToOne
    @JoinColumn(name = "habitId")
    private Tracker tracker;
}
