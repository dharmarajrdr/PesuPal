package com.pesupal.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pesupal.server.model.Feed;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

}
