package com.pesupal.server.dto.response;

import com.pesupal.server.model.post.Poll;
import com.pesupal.server.model.post.PollOption;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class PollDto {

    private String question;

    private Map<String, Integer> votes;

    public static PollDto fromPoll(Poll poll) {
        List<PollOption> pollOptions = poll.getOptions();
        Map<String, Integer> votesMap = new HashMap<>();
        for (PollOption option : pollOptions) {
            votesMap.put(option.getOption(), option.getVoters().size());
        }
        PollDto pollDto = new PollDto();
        pollDto.setQuestion(poll.getQuestion());
        pollDto.setVotes(votesMap);
        return pollDto;
    }
}
