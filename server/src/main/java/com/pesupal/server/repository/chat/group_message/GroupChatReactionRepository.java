package com.pesupal.server.repository.chat.group_message;

import com.pesupal.server.model.chat.group_message.GroupChatMessage;
import com.pesupal.server.model.chat.group_message.GroupChatReaction;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.projections.ReactionCountProjection;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupChatReactionRepository extends JpaRepository<GroupChatReaction, Long> {


    @Query("SELECT dr.reaction AS reaction, COUNT(dr.id) AS count FROM GroupChatReaction dr WHERE dr.groupChatMessage.id = :messageId GROUP BY dr.reaction")
    List<ReactionCountProjection> findReactionCountsByMessageId(@Param("messageId") Long messageId);

    Optional<GroupChatReaction> findByGroupChatMessageAndReactedBy(GroupChatMessage groupChatMessage, OrgMember reactor);
}
