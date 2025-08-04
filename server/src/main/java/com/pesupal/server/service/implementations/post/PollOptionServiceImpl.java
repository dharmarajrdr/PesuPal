package com.pesupal.server.service.implementations.post;

import com.pesupal.server.repository.post.PollOptionRepository;
import com.pesupal.server.service.interfaces.post.PollOptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PollOptionServiceImpl implements PollOptionService {

    private final PollOptionRepository pollOptionRepository;
}
