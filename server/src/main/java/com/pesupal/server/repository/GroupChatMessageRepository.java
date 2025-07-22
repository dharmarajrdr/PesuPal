package com.pesupal.server.repository;

import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupChatMessageRepository extends JpaRepository<GroupChatMessage, Long> {

    void deleteAllByGroup(Group group);
}
