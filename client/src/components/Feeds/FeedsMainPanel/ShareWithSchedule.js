import { useState } from 'react'
import { useDispatch } from 'react-redux';
import SchedulePicker from '../../Chat/ConversationScreen/SchedulePicker';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';

const ShareWithSchedule = ({ onShare, onSchedule, isPostCreation }) => {

    const dispatch = useDispatch();
    const [showPicker, setShowPicker] = useState(false);
    const [showSchedule, setShowSchedule] = useState(false);

    const shareClickHandler = () => {
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to post this?',
            options: [
                {
                    title: 'Post',
                    color: 'green',
                    onClick: onShare
                },
                {
                    title: 'Cancel',
                    color: 'gray',
                    onClick: () => dispatch(hideConfirmationPopup())
                }
            ]
        }));
    }

    const scheduleClickHandler = (scheduledAt) => {
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to schedule this post?',
            options: [
                {
                    title: 'Schedule',
                    color: 'green',
                    onClick: () => onSchedule(scheduledAt)
                },
                {
                    title: 'Cancel',
                    color: 'gray',
                    onClick: () => dispatch(hideConfirmationPopup())
                }
            ]
        }));
    }

    return (
        <div className={`${isPostCreation ? 'create-post' : 'update-post'}-wrapper FCSS w100`}>
            <div className="FRCC w100" id='share-post-button-wrapper'>
                <button className="share-main" onClick={shareClickHandler}>{isPostCreation ? 'Share' : 'Update'}</button>
                {isPostCreation && <i className={`fa ${showSchedule ? 'fa-chevron-up' : 'fa-chevron-down'}`} id="share-chevron" onClick={() => setShowSchedule(prev => !prev)}></i>}
            </div>

            {onSchedule && showPicker && <SchedulePicker onSchedule={scheduleClickHandler} showPicker={showPicker} setShowPicker={setShowPicker} />}
            {isPostCreation && <div className={`w100 schedule-slide ${showSchedule ? 'slide-visible' : ''}`}>
                <button className="schedule-btn" onClick={() => setShowPicker(!showPicker)}>Schedule</button>
            </div>}
        </div>
    );
};

export default ShareWithSchedule;