package com.pesupal.server.repository;

import com.pesupal.server.model.group.GroupChatPinned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupChatPinnedRepository extends JpaRepository<GroupChatPinned, Long> {
}
