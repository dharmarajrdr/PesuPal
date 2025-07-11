package com.pesupal.server.repository;

import com.pesupal.server.model.workdrive.FileAccessStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileAccessStatRepository extends JpaRepository<FileAccessStat, Long> {

    List<FileAccessStat> findAllByFileIdOrderByCreatedAtDesc(Long fileId);

    @Query(value = """
                SELECT DISTINCT ON (file_id) *
                FROM file_access_stat
                WHERE user_id = :userId
                ORDER BY file_id, created_at DESC
                LIMIT :limit
            """, nativeQuery = true)
    List<FileAccessStat> findRecentlyAccessedFilesByUserIdLimit(@Param("userId") Long userId, @Param("limit") int limit);


}
