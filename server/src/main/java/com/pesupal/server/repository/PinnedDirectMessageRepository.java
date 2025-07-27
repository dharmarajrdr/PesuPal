package com.pesupal.server.repository;

import com.pesupal.server.model.chat.DirectMessageChat;
import com.pesupal.server.model.chat.PinnedDirectMessage;
import com.pesupal.server.model.user.OrgMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PinnedDirectMessageRepository extends JpaRepository<PinnedDirectMessage, Long> {

    boolean existsByPinnedByAndChat_PublicId(OrgMember orgMember, String chatId);

    List<PinnedDirectMessage> findAllByPinnedByOrderByOrderIndexAsc(OrgMember pinnedBy);

    Optional<PinnedDirectMessage> findByPinnedByAndChat(OrgMember pinnedBy, DirectMessageChat chat);
}
