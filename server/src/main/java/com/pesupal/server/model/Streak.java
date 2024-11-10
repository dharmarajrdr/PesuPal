package com.pesupal.server.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "streak", uniqueConstraints = @UniqueConstraint(columnNames = {"habit_id", "streak_date"}))
public class Streak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer streakId;

    @Column(name = "streak_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date streakDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Days streakDay;

    @ManyToOne
    @JoinColumn(name = "habit_id", nullable = false)
    @JsonIgnore
    private Tracker tracker;
}
