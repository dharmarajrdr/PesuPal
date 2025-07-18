package com.pesupal.server.dto.request;

import lombok.Data;

@Data
public class CreatePollVoterDto {

    private Long pollId;

    private Long optionId;
}
