package com.pesupal.server.repository;

import com.pesupal.server.model.chat.DirectMessageChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirectMessageChatRepository extends JpaRepository<DirectMessageChat, Long> {

    List<DirectMessageChat> findByUser1_PublicIdAndUser2_PublicId(String user1PublicId, String user2PublicId);

    @Query("""
            SELECT d FROM DirectMessageChat d
            WHERE 
                (d.user1.publicId = :orgMemberPublicId1 AND d.user2.publicId = :orgMemberPublicId2)
            OR 
                (d.user1.publicId = :orgMemberPublicId2 AND d.user2.publicId = :orgMemberPublicId1)
            """)
    Optional<DirectMessageChat> findByParticipants(@Param("orgMemberPublicId1") String orgMemberPublicId1, @Param("orgMemberPublicId2") String orgMemberPublicId2);

    Optional<DirectMessageChat> findByPublicId(String publicId);
}
