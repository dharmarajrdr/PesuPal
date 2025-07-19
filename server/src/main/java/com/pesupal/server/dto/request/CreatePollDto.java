package com.pesupal.server.dto.request;

import com.pesupal.server.enums.Visibility;
import com.pesupal.server.model.post.Poll;
import lombok.Data;

import java.util.Set;

@Data
public class CreatePollDto {

    private String question;

    private Set<String> options;

    private Visibility votersVisibility = Visibility.PRIVATE;

    private boolean votesUpdatable = false;

    public Poll toPoll() {

        Poll poll = new Poll();
        poll.setQuestion(this.question);
        poll.setVotersVisibility(this.votersVisibility);
        poll.setVotesUpdatable(this.votesUpdatable);
        return poll;
    }
}
