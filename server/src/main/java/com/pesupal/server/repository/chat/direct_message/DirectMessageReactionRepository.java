package com.pesupal.server.repository.chat.direct_message;

import com.pesupal.server.model.chat.direct_message.DirectMessage;
import com.pesupal.server.model.chat.direct_message.DirectMessageReaction;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.projections.ReactionCountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirectMessageReactionRepository extends JpaRepository<DirectMessageReaction, Long> {

    Optional<DirectMessageReaction> findByDirectMessageAndReactor(DirectMessage directMessage, OrgMember orgMember);

    @Query("SELECT dr.reaction AS reaction, COUNT(dr.id) AS count FROM DirectMessageReaction dr WHERE dr.directMessage.id = :messageId GROUP BY dr.reaction")
    List<ReactionCountProjection> findReactionCountsByMessageId(@Param("messageId") Long messageId);

}
