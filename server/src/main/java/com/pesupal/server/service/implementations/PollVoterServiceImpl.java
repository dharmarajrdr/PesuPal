package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreatePollVoterDto;
import com.pesupal.server.dto.response.PollDto;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.post.Poll;
import com.pesupal.server.model.post.PollOption;
import com.pesupal.server.model.post.PollVoter;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.PollVoterRepository;
import com.pesupal.server.service.interfaces.PollService;
import com.pesupal.server.service.interfaces.PollVoterService;
import com.pesupal.server.service.interfaces.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PollVoterServiceImpl extends CurrentValueRetriever implements PollVoterService {

    private final PostService postService;
    private final PollService pollService;
    private final PollVoterRepository pollVoterRepository;

    /**
     * Creates a new poll voter entry based on the provided DTO.
     *
     * @param createPollVoterDto
     * @return
     */
    @Override
    public PollDto createPollVoter(CreatePollVoterDto createPollVoterDto) {

        OrgMember orgMember = getCurrentOrgMember();
        Long orgId = orgMember.getOrg().getId();
        Long userId = orgMember.getUser().getId();

        Poll poll = pollService.getPollById(createPollVoterDto.getPollId());
        if (!poll.getId().equals(createPollVoterDto.getPollId())) {
            throw new IllegalArgumentException("Poll ID does not match the post's poll.");
        }

        Post post = postService.getPostByIdAndOrgId(poll.getPost().getId(), orgId);
        if (!post.isHasPoll()) {
            throw new ActionProhibitedException("This post does not have a poll.");
        }

        PollOption pollOption = poll.getOptions().stream().filter(option -> option.getId().equals(createPollVoterDto.getOptionId())).findFirst().orElseThrow(() -> new IllegalArgumentException("Poll option with ID " + createPollVoterDto.getOptionId() + " does not exist in the poll."));

        PollVoter pollVoter;
        Optional<PollVoter> pollVoterOptional = pollVoterRepository.findByPollOption_PollIdAndVoter_Id(poll.getId(), userId);

        if (pollVoterOptional.isPresent()) {
            if (!poll.getVotesUpdatable()) {
                throw new ActionProhibitedException("You have already voted in this poll and votes are not updatable.");
            }
            pollVoter = pollVoterOptional.get();
            if (pollVoter.getPollOption().getId().equals(createPollVoterDto.getOptionId())) {
                throw new ActionProhibitedException("Vote already recorded.");
            }
            pollVoter.setPollOption(pollOption);
        } else {
            pollVoter = new PollVoter();
            pollVoter.setVoter(orgMember);
            pollVoter.setPollOption(pollOption);
        }
        pollVoter = pollVoterRepository.save(pollVoter);
        return PollDto.fromPoll(pollVoter.getPollOption().getPoll(), userId);
    }
}
