import { useState } from 'react';
import Profile from '../../OthersProfile/Profile';
import UserAvatar from '../../User/UserAvatar';
import './ChatHeader.css';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { setShowChatHeaderOptionsModal } from '../../../store/reducers/ShowChatHeaderOptionsModalSlice';
import OptionsModal from '../../Utils/OptionsModal';

const ChatHeader = ({ activeRecentChatState, activeChatPreview, setCurrentChatId }) => {

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const showChatHeaderOptionsModalSlice = useSelector(state => state.showChatHeaderOptionsModalSlice);
    const [showProfile, setShowProfile] = useState(false);
    const [, setActiveRecentChat] = activeRecentChatState;
    const { id: otherUserId, displayName, displayPicture } = activeChatPreview?.otherUser || {};

    const closeChatHandler = () => {
        setCurrentChatId(null);
        setActiveRecentChat(null);
        navigate('/chat');
    }

    const chatHeaderOptionsClickHandler = () => {
        dispatch(setShowChatHeaderOptionsModal(!showChatHeaderOptionsModalSlice));
    }

    const options = [
        { name: 'Pin Conversation', icon: 'fa fa-thumbtack' },
        { name: 'View Media', icon: 'fa fa-image' }
    ];

    return (
        <div className="chat-header FRCB w100">
            {showProfile && <Profile userId={otherUserId} setShowProfile={setShowProfile} />}
            <div className='FRCS'>
                <UserAvatar displayPicture={displayPicture} displayName={displayName} setShowProfile={setShowProfile} />
                <div className="name">{displayName}</div>
            </div>
            <div className='FRCE'>
                {showChatHeaderOptionsModalSlice && <OptionsModal options={options} />}
                <i className='header-icons fa fa-ellipsis-v' id='chat-header-options' onClick={chatHeaderOptionsClickHandler} />
                <i className='header-icons fa fa-close mL10' id='close-chat' onClick={closeChatHandler}></i>
            </div>
        </div>
    );
};


export default ChatHeader