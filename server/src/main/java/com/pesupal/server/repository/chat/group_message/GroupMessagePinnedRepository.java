package com.pesupal.server.repository.chat.group_message;

import com.pesupal.server.model.chat.group_message.GroupMessagePinned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMessagePinnedRepository extends JpaRepository<GroupMessagePinned, Long> {
}
