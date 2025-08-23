import React, { useState } from 'react'
import { useDispatch } from 'react-redux';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';
import SchedulePicker from '../../Chat/ConversationScreen/SchedulePicker';

const ShareWithSchedule = ({ onShare, onSchedule }) => {

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
                    onClick: () => {
                        onShare(() => {
                            dispatch(hideConfirmationPopup());
                        }, () => {
                            dispatch(hideConfirmationPopup());
                        });
                    }
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
                    onClick: () => {
                        onSchedule(scheduledAt, () => {
                            dispatch(hideConfirmationPopup());
                        }, () => {
                            dispatch(hideConfirmationPopup());
                        });
                    }
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
        <div className="share-wrapper FCSS w100">
            <div className="FRCC w100" id='share-post-button-wrapper'>
                <button className="share-main" onClick={shareClickHandler}>Share</button>
                <i className={`fa ${showSchedule ? 'fa-chevron-up' : 'fa-chevron-down'}`} id="share-chevron" onClick={() => setShowSchedule(prev => !prev)}></i>
            </div>

            {showPicker && <SchedulePicker onSchedule={scheduleClickHandler} showPicker={showPicker} setShowPicker={setShowPicker} />}
            <div className={`w100 schedule-slide ${showSchedule ? 'slide-visible' : ''}`}>
                <button className="schedule-btn" onClick={() => setShowPicker(!showPicker)}>Schedule</button>
            </div>
        </div>
    );
};

export default ShareWithSchedule;