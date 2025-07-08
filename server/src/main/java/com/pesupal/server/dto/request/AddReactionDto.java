package com.pesupal.server.dto.request;

import com.pesupal.server.enums.Reaction;
import lombok.Data;

@Data
public class AddReactionDto {

    Long userId;

    Reaction reaction;
}
