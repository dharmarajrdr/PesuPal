package com.pesupal.server.repository.chat.direct_message;

import com.pesupal.server.model.chat.direct_message.DirectMessageChat;
import com.pesupal.server.model.chat.direct_message.PinnedDirectMessage;
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
