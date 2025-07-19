package com.pesupal.server.service.implementations;

import com.pesupal.server.repository.PollOptionRepository;
import com.pesupal.server.service.interfaces.PollOptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PollOptionServiceImpl implements PollOptionService {

    private final PollOptionRepository pollOptionRepository;
}
