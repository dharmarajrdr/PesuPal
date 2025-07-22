package com.pesupal.server.repository;

import com.pesupal.server.model.group.GroupChatReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupChatReactionRepository extends JpaRepository<GroupChatReaction, Long> {
}
