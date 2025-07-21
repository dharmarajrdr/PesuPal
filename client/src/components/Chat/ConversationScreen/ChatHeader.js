import { useState } from 'react';
import Profile from '../../OthersProfile/Profile';
import UserAvatar from '../../User/UserAvatar';
import './ChatHeader.css';
import { useNavigate } from 'react-router-dom';

const ChatHeader = ({ activeRecentChatState, activeChatPreview, setCurrentChatId }) => {

    const navigate = useNavigate();
    const [showProfile, setShowProfile] = useState(false);
    const [, setActiveRecentChat] = activeRecentChatState;
    const { id: otherUserId, displayName, displayPicture } = activeChatPreview?.otherUser || {};

    const closeChatHandler = () => {
        setCurrentChatId(null);
        setActiveRecentChat(null);
        navigate('/chat');
    }

    return (
        <div className="chat-header FRCB w100">
            {showProfile && <Profile userId={otherUserId} setShowProfile={setShowProfile} />}
            <div className='FRCS'>
                <UserAvatar displayPicture={displayPicture} displayName={displayName} setShowProfile={setShowProfile} />
                <div className="name">{displayName}</div>
            </div>
            <div>
                <i className='fa fa-close' id='close-chat' onClick={closeChatHandler}></i>
            </div>
        </div>
    );
};


export default ChatHeader