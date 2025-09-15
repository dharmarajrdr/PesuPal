import { useState } from 'react'
import { useDispatch } from 'react-redux';
import SchedulePicker from '../../Chat/ConversationScreen/SchedulePicker';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';

const ShareWithSchedule = ({ scheduleClickHandler, shareClickHandler, scheduledAt }) => {

    const dispatch = useDispatch();
    const [showPicker, setShowPicker] = useState(false);
    const [showSchedule, setShowSchedule] = useState(false);

    

    return (
        <div className={`create-post-wrapper FCSS w100`}>
            <div className="FRCC w100" id='share-post-button-wrapper'>
                <button className="share-main" onClick={shareClickHandler}>Share</button>
                {<i className={`fa ${showSchedule ? 'fa-chevron-up' : 'fa-chevron-down'}`} id="share-chevron" onClick={() => setShowSchedule(prev => !prev)}></i>}
            </div>

            {showPicker && <SchedulePicker onSchedule={scheduleClickHandler} showPicker={showPicker} setShowPicker={setShowPicker} defaultTime={scheduledAt} />}
            {<div className={`w100 schedule-slide ${showSchedule ? 'slide-visible' : ''}`}>
                <button className="schedule-btn" onClick={() => setShowPicker(!showPicker)}>Schedule</button>
            </div>}
        </div>
    );
};

export default ShareWithSchedule;