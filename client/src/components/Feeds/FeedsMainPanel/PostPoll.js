import './PostPoll.css';
import utils from '../../../utils';
import { useDispatch } from "react-redux";
import { showPopup } from '../../../store/reducers/PopupSlice';

const PollOption = ({ option, index, options, setOptions }) => {

    const handleOptionChange = (e) => {
        const newOptions = [...options];
        newOptions[index] = e.target.value;
        setOptions(newOptions);
    }

    const removeOptionHandler = (index) => {
        const newOptions = options.filter((_, i) => i !== index);
        setOptions(newOptions);
    }

    return (
        <div className='create-poll-option FRCS pR'>
            <input type='radio' name='poll_option' className='mR10' disabled />
            <input type='text' spellCheck='false' autoComplete='off' className='w100 create-poll-option-input input-focus-beauty-border' placeholder={`Enter option ${index + 1}`} name='poll' value={option} id={`poll_option_${index}`} onChange={handleOptionChange} />
            {index > 1 && <i className='fa fa-minus-circle remove-poll-option-icon' onClick={() => removeOptionHandler(index)}></i>}
        </div>
    );
}

const PostPoll = ({ question, setQuestion, options, setOptions }) => {

    const maxOptions = 4;
    const dispatch = useDispatch();

    const addNewOptionHandler = () => {
        if (options.length >= maxOptions) {
            return dispatch(showPopup({ message: `You can only add up to ${maxOptions} options.`, type: 'error' }));
        }
        setOptions([...options, '']);
        utils.autoFocusInput(`poll_option_${options.length}`);
    }

    return (
        <div id='post_poll_container' className='FCSC'>
            <div className='FRCB w100'>
                <input type='text' placeholder='Ask a question...' value={question} onChange={(e) => setQuestion(e.target.value)} id='poll-question-input' />
                <button className='FRCS' id='add-poll-option-button' onClick={addNewOptionHandler}>
                    <i className='fa fa-plus w15 mR5'></i>
                    <span>Add Option</span>
                </button>
            </div>
            <div className='FRSB w100 mT10' id='create-poll-options'>
                {options.map((option, index) => (
                    <PollOption key={index} option={option} index={index} options={options} setOptions={setOptions} />
                ))}
            </div>
        </div>
    )
}

export default PostPoll