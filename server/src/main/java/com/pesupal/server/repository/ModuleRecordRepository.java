package com.pesupal.server.repository;

import com.pesupal.server.model.module.ModuleRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRecordRepository extends JpaRepository<ModuleRecord, Long> {
}
