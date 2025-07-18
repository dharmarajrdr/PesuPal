package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.post.PollOption;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PollOptionDto {

    private Long id;

    private String option;

    private Integer voteCount;

    public static PollOptionDto fromPollOption(PollOption pollOption, boolean revealVotesCount) {

        PollOptionDto dto = new PollOptionDto();
        dto.setId(pollOption.getId());
        dto.setOption(pollOption.getOption());
        if (revealVotesCount) {
            dto.setVoteCount(pollOption.getVoters().size());
        }
        return dto;
    }
}
