package com.pesupal.server.dto.request.post;

import com.pesupal.server.enums.Reaction;
import lombok.Data;

@Data
public class AddReactionDto {

    Reaction reaction;
}
