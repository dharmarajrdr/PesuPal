package com.pesupal.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Data
@AllArgsConstructor
public class RecentChatPagedDto {

    List<RecentChatDto> chats;

    Pageable pageable;

    Long total;
}
