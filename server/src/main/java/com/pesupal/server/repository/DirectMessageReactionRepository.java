package com.pesupal.server.repository;

import com.pesupal.server.model.chat.DirectMessage;
import com.pesupal.server.model.chat.DirectMessageReaction;
import com.pesupal.server.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectMessageReactionRepository extends JpaRepository<DirectMessageReaction, Long> {

    Optional<DirectMessageReaction> findByDirectMessageAndUser(DirectMessage directMessage, User user);
}
