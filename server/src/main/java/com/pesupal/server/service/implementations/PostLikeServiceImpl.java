package com.pesupal.server.service.implementations;

import com.pesupal.server.repository.PostLikeRepository;
import com.pesupal.server.service.interfaces.PostLikeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
}
