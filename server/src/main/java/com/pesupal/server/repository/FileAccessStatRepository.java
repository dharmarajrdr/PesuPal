package com.pesupal.server.repository;

import com.pesupal.server.model.workdrive.FileAccessStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileAccessStatRepository extends JpaRepository<FileAccessStat, Long> {

    List<FileAccessStat> findAllByFileIdOrderByCreatedAtDesc(Long fileId);
}
