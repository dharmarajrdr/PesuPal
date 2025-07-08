package com.pesupal.server.dto.response;

import com.pesupal.server.enums.Reaction;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReactMessageResponseDto {

    private Long reactionId;

    private Reaction reaction;

    private LocalDateTime reactedAt;

    private UserBasicInfoDto reactor;
}
