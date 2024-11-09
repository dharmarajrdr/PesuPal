package com.pesupal.server.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pesupal.server.model.Tracker;

@Repository
public interface TrackerRepository extends JpaRepository<Tracker, Integer> {

    @Query("SELECT DISTINCT t FROM Tracker t WHERE t.userId = :userId")
    List<Tracker> findAllByUserId(Integer userId, Pageable pageable);

}
