import './PeopleCard.css';
import { useState } from 'react';
import { useDispatch } from "react-redux";
import { useNavigate } from 'react-router-dom';
import { StatusIndicator } from '../../Auth/utils';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { showProfile } from '../../../store/reducers/ProfileSlice';

const FirstChar = ({ name }) => {

    return <span className='first-char'>
        {name.trim().toUpperCase().charAt(0)}
    </span>
}

const PeopleCard = ({ person }) => {

    const { displayName, displayPicture, designation, status, userId, chatId } = person || {};

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [showDisplayPicture, setShowDisplayPicture] = useState(displayPicture != null);

    const redirectToChatHandler = (e) => {
        e.stopPropagation();
        if (chatId == null) {
            return dispatch(showPopup({ 'message': 'Chat is not available for this user', 'type': 'error' }));
        }
        navigate(`/chat/messages/${chatId}`);
    }

    const handleProfileClick = (e) => {
        e.stopPropagation();
        dispatch(showProfile(userId));
    }

    return (
        <div className='FCCC PeopleCard' onClick={handleProfileClick}>
            <i className="fa fa-ellipsis-vertical three_dots"></i>
            <div className='FCCC mB5 img_name_dept'>
                <div className='FRCC profile_picture_container mB10'>
                    {showDisplayPicture ? <img src={displayPicture} className='img_75_75' onError={() => setShowDisplayPicture(false)} /> : <FirstChar name={displayName} />}
                    <StatusIndicator status={status} style={{ padding: '3px', bottom: '3px', right: '3px' }} />
                </div>
                <b className='mx5 user_name'>{displayName}</b>
                <span className='color777 fs10 mB5 designation' title={designation}>{designation}</span>
            </div>
            <div className='mT5 FRCC'>
                <i className='profile_contacts fa fa-comment' style={{ backgroundColor: '#3591ff' }} onClick={redirectToChatHandler} />
                <i className='profile_contacts fa fa-phone' style={{ backgroundColor: 'green' }} />
                <i className='profile_contacts fa fa-video' style={{ backgroundColor: 'red' }} />
            </div>
        </div>
    )
}

export default PeopleCard