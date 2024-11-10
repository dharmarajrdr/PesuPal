package com.pesupal.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pesupal.server.model.Streak;

@Repository
public interface StreakRepository extends JpaRepository<Streak, Integer> {

}
