package com.pesupal.server.repository;

import com.pesupal.server.model.chat.DirectMessage;
import com.pesupal.server.model.chat.DirectMessageMediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectMessageMediaFileRepository extends JpaRepository<DirectMessageMediaFile, Long> {

    DirectMessageMediaFile findByDirectMessage(DirectMessage dm);
}
