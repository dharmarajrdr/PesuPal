package com.pesupal.server.service.interfaces.post;

import com.pesupal.server.dto.request.post.CreatePollDto;
import com.pesupal.server.dto.request.post.UpdatePollDto;
import com.pesupal.server.model.post.Poll;
import com.pesupal.server.model.post.Post;

public interface PollService {

    Poll getPollByPost(Post post);

    Poll createPoll(CreatePollDto poll, Post post);

    void validateNewPoll(CreatePollDto poll);

    void deleteByPost(Post post);

    Poll getPollById(Long pollId);

    void updatePoll(Long pollId, UpdatePollDto updatePollDto);
}
