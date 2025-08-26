import './PostPoll.css';
import { useState } from 'react';

const PollOption = ({ option, index }) => {

    return (
        <div className='create-poll-option FRCS pR'>
            <input type='radio' name='poll_option' className='mR10' disabled />
            <input type='text' className='w100 create-poll-option-input input-focus-beauty-border' placeholder='Enter option' name='poll' value={option} id={`poll_option_${index}`} />
            <i className='fa fa-minus-circle remove-poll-option-icon'></i>
        </div>
    );
}

const PostPoll = () => {

    const [question, setQuestion] = useState('Who will win the World Cup 2026?');
    const [options, setOptions] = useState(['India', 'Australia', 'EnglandEnglandEnglandEnglandEnglandEnglandEnglandEnglandEnglandEnglandEngland', 'New Zealand']);

    return (
        <div id='post_poll_container' className='FCSC'>
            <div className='FRCB w100'>
                <input type='text' placeholder='Ask a question...' value={question} onChange={(e) => setQuestion(e.target.value)} id='poll-question-input' />
                <button className='FRCS' id='add-poll-option-button'>
                    <i className='fa fa-plus w15 mR5'></i>
                    <span>Add Option</span>
                </button>
            </div>
            <div className='FRSB w100 mT10' id='create-poll-options'>
                {options.map((option, index) => (
                    <PollOption key={index} option={option} index={index} />
                ))}
            </div>
        </div>
    )
}

export default PostPoll