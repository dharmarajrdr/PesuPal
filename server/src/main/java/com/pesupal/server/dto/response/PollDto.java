package com.pesupal.server.dto.response;

import com.pesupal.server.model.post.Poll;
import com.pesupal.server.model.post.PollOption;
import com.pesupal.server.model.post.PollVoter;
import lombok.Data;

import java.util.List;

@Data
public class PollDto {

    private Long id;

    private String question;

    private List<PollOptionDto> options;

    private Long votedOptionId;

    public static PollDto fromPoll(Poll poll, Long userId) {

        PollDto pollDto = new PollDto();
        pollDto.setId(poll.getId());
        pollDto.setQuestion(poll.getQuestion());

        boolean revealVotes = false;
        Long votedOptionId = null;

        outer:
        for (PollOption option : poll.getOptions()) {
            for (PollVoter voter : option.getVoters()) {
                if (voter.getVoter().getId().equals(userId)) {
                    votedOptionId = option.getId();
                    revealVotes = true;
                    break outer; // early exit both loops
                }
            }
        }

        pollDto.setVotedOptionId(votedOptionId);
        boolean finalRevealVotes = revealVotes;
        pollDto.setOptions(poll.getOptions().stream().map(opt -> PollOptionDto.fromPollOption(opt, finalRevealVotes)).toList());

        return pollDto;
    }

}
