import { useState } from "react";
import "./Poll.css";

const Poll = ({ poll, setPoll }) => {

    const [selectedOption, setSelectedOption] = useState(null);
    const [voted, setVoted] = useState(false);

    const totalVotes = Object.values(poll.votes).reduce((a, b) => a + b, 0);

    const handleVote = (option) => {
        if (voted) return;

        setPoll((prev) => ({
            ...prev,
            votes: {
                ...prev.votes,
                [option]: prev.votes[option] + 1
            }
        }));

        setSelectedOption(option);
        setVoted(true);
    };

    return (
        <div className="post-poll">
            <h4 className="poll-question">{poll.question}</h4>
            <div className="poll-options">
                {Object.entries(poll.votes).map(([option, count]) => {
                    const percentage = totalVotes === 0 ? 0 : Math.round((count / totalVotes) * 100);

                    return (
                        <div key={option} className={`poll-option ${selectedOption === option ? 'selected' : ''} FRCB`}
                            onClick={() => handleVote(option)}>
                            <div className="poll-bar-container">
                                <div className="poll-bar" style={{ width: `${percentage}%` }}></div>
                                <span className="poll-option-text">{option}</span>
                            </div>
                            {voted && <span className="poll-percentage">{percentage}%</span>}
                        </div>
                    );
                })}
            </div>
            {voted && <p className="poll-total-votes">Total Voters: {totalVotes}</p>}
        </div>
    );
};

export default Poll;
