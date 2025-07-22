package com.pesupal.server.repository;

import com.pesupal.server.model.group.GroupMessagePinned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMessagePinnedRepository extends JpaRepository<GroupMessagePinned, Long> {
}
