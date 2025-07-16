package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.PinnedDirectMessageDto;

import java.util.List;

public interface PinnedDirectMessageService {

    List<PinnedDirectMessageDto> getAllPinnedDirectMessages(Long currentUserId, Long currentOrgId);
}
