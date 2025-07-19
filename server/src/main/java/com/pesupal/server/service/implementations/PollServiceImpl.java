package com.pesupal.server.service.implementations;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.dto.request.CreatePollDto;
import com.pesupal.server.dto.request.UpdatePollDto;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.post.Poll;
import com.pesupal.server.model.post.PollOption;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.repository.PollRepository;
import com.pesupal.server.service.interfaces.OrgMemberService;
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
    private final OrgMemberService orgMemberService;

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

    private List<PollOption> convertToPollOptions(Set<String> options, Poll poll) {
        return options.stream().map(option -> {
            PollOption pollOption = new PollOption();
            pollOption.setOption(option);
            pollOption.setPoll(poll);
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
        poll.setOptions(convertToPollOptions(createPollDto.getOptions(), poll));
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

    /**
     * Fetches a poll by its ID.
     *
     * @param pollId
     * @return
     */
    @Override
    public Poll getPollById(Long pollId) {

        return pollRepository.findById(pollId).orElseThrow(() -> new DataNotFoundException("Poll with ID " + pollId + " not found"));
    }

    /**
     * Updates an existing poll with the provided data.
     *
     * @param pollId
     * @param updatePollDto
     * @param userId
     * @param orgId
     */
    @Override
    public void updatePoll(Long pollId, UpdatePollDto updatePollDto, Long userId, Long orgId) {

        orgMemberService.validateUserIsOrgMember(userId, orgId);
        Poll poll = getPollById(pollId);
        if (!poll.getPost().getUser().getId().equals(userId)) {
            throw new PermissionDeniedException("You do not have permission to update this poll");
        }

        updatePollDto.applyToPoll(poll);
        pollRepository.save(poll);
    }
}
