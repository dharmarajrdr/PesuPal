package com.pesupal.server.dto.request;

import com.pesupal.server.enums.Visibility;
import com.pesupal.server.model.post.Poll;
import lombok.Data;

@Data
public class UpdatePollDto {

    private Visibility votersVisibility;

    private Boolean votesUpdatable;

    public void applyToPoll(Poll poll) {
        if (votersVisibility != null) {
            poll.setVotersVisibility(votersVisibility);
        }
        if (votesUpdatable != null) {
            poll.setVotesUpdatable(votesUpdatable);
        }
    }
}
