package com.pesupal.server.repository.chat.direct_message;

import com.pesupal.server.model.chat.direct_message.DirectMessage;
import com.pesupal.server.model.chat.direct_message.DirectMessageMediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirectMessageMediaFileRepository extends JpaRepository<DirectMessageMediaFile, Long> {

    Optional<DirectMessageMediaFile> findByDirectMessage(DirectMessage directMessage);

    List<DirectMessageMediaFile> findAllByDirectMessageIsNull();
}
