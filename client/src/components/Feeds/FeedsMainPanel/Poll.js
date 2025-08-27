import { useState } from "react";
import "./Poll.css";
import { apiRequest } from "../../../http_request";
import { useDispatch } from "react-redux";
import { showPopup } from "../../../store/reducers/PopupSlice";

const Poll = ({ poll, setPoll }) => {

    const dispatch = useDispatch();
    const [selectedOptionId, setSelectedOptionId] = useState(poll.votedOptionId || null);
    const [voted, setVoted] = useState(poll.votedOptionId);

    const { question, options } = poll || {};

    const totalVotes = options.reduce((acc, option) => acc + option.voteCount, 0);

    const handleVote = ({ id }) => {

        if (selectedOptionId === id) {
            dispatch(showPopup({ message: "You have already voted for this option.", type: 'error' }));
            return;
        }

        apiRequest(`/api/v1/post/poll/vote`, 'POST', { "pollId": poll.id, "optionId": id }).then(({ data }) => {

            setPoll(data);
            setVoted(true);
            setSelectedOptionId(id);

        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });

    };

    return (
        <div className="post-poll">
            <h4 className="poll-question">{question}</h4>
            <div className="poll-options">
                {options.map(({ id, option, voteCount }) => {
                    const percentage = totalVotes === 0 ? 0 : Math.round((voteCount / totalVotes) * 100);
                    return (
                        <div key={option} className={`poll-option ${selectedOptionId === id ? 'selected' : ''} FRCB`} onClick={() => handleVote({ id })}>
                            <div className="poll-bar-container">
                                <div className="poll-bar" style={{ width: `${percentage}%` }}></div>
                                <span className="poll-option-text">{option}</span>
                            </div>
                            {voted && <span className="poll-percentage">{percentage}%</span>}
                        </div>
                    );
                })}
            </div>
            {voted && <p className="poll-total-votes">{totalVotes} votes</p>}
        </div>
    );
};

export default Poll;
