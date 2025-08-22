package com.pesupal.server.service.interfaces.post;

import com.pesupal.server.dto.request.post.CreatePollVoterDto;
import com.pesupal.server.dto.response.post.PollDto;

public interface PollVoterService {

    PollDto createPollVoter(CreatePollVoterDto createPollVoterDto);
}
