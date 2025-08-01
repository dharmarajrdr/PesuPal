package com.pesupal.server.repository;

import com.pesupal.server.model.module.ModuleRecordTimeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRecordTimelineRepository extends JpaRepository<ModuleRecordTimeline, Long> {

    List<ModuleRecordTimeline> findAllByRecord_PublicIdOrderByCreatedAt(String moduleRecordId);
}
