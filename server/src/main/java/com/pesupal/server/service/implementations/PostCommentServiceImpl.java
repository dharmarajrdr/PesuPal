package com.pesupal.server.service.implementations;

import com.pesupal.server.repository.PostCommentRepository;
import com.pesupal.server.service.interfaces.PostCommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {

    private final PostCommentRepository postCommentRepository;
}
