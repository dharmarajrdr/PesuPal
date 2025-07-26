package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreatePollVoterDto;
import com.pesupal.server.dto.response.PollDto;

public interface PollVoterService {

    PollDto createPollVoter(CreatePollVoterDto createPollVoterDto);
}
