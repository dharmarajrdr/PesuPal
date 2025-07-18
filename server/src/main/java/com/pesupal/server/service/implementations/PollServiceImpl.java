package com.pesupal.server.service.implementations;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.dto.request.CreatePollDto;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.post.Poll;
import com.pesupal.server.model.post.PollOption;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.repository.PollRepository;
import com.pesupal.server.service.interfaces.PollService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;

    /**
     * Fetches the poll associated with a given post.
     *
     * @param post
     * @return
     */
    @Override
    public Poll getPollByPost(Post post) {

        return pollRepository.findByPost(post).orElseThrow(() -> new DataNotFoundException("No poll found for this post"));
    }

    private List<PollOption> convertToPollOptions(Set<String> options) {
        return options.stream().map(option -> {
            PollOption pollOption = new PollOption();
            pollOption.setOption(option);
            return pollOption;
        }).collect(Collectors.toList());
    }

    /**
     * Creates a new poll for a given post.
     *
     * @param createPollDto
     * @param post
     * @return
     */
    @Override
    public Poll createPoll(CreatePollDto createPollDto, Post post) {

        Poll poll = createPollDto.toPoll();
        poll.setPost(post);
        poll.setOptions(convertToPollOptions(createPollDto.getOptions()));
        return pollRepository.save(poll);
    }

    /**
     * Validates the new poll data before creation.
     *
     * @param poll
     */
    @Override
    public void validateNewPoll(CreatePollDto poll) {
        if (poll == null) {
            return;
        }
        if (poll.getQuestion() == null || poll.getQuestion().isBlank()) {
            throw new ActionProhibitedException("Poll question cannot be empty");
        }
        if (poll.getOptions() == null || poll.getOptions().size() < 2) {
            throw new ActionProhibitedException("Poll should have at least two options");
        }
        if (poll.getOptions().size() > StaticConfig.MAXIMUM_OPTIONS_PER_POLL) {
            throw new ActionProhibitedException("Poll cannot have more than " + StaticConfig.MAXIMUM_OPTIONS_PER_POLL + " options");
        }
    }

    /**
     * Deletes the poll associated with a given post.
     *
     * @param post
     */
    @Override
    public void deleteByPost(Post post) {

        Poll poll = pollRepository.findByPost(post).orElseThrow(() -> new DataNotFoundException("No poll associated with this post"));
        pollRepository.delete(poll);
    }
}
