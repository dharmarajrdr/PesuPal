package com.pesupal.server.service.implementations;

import com.pesupal.server.repository.PollVoterRepository;
import com.pesupal.server.service.interfaces.PollVoterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PollVoterServiceImpl implements PollVoterService {

    private final PollVoterRepository pollVoterRepository;
}
