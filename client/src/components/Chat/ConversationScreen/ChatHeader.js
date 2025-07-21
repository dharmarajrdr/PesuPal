import UserAvatar from '../../User/UserAvatar';
import './ChatHeader.css';
import { useNavigate } from 'react-router-dom';

const ChatHeader = ({ activeRecentChatState, activeChatPreview }) => {

    const navigate = useNavigate();
    const [, setActiveRecentChat] = activeRecentChatState;
    const { id, displayName, displayPicture } = activeChatPreview?.otherUser || {};

    const closeChatHandler = () => {
        navigate('/chat');
        setActiveRecentChat(null);
    }

    return (
        <div className="chat-header FRCB w100">
            <div className='FRCS'>
                <UserAvatar displayPicture={displayPicture} displayName={displayName} />
                <div className="name">{displayName}</div>
            </div>
            <div>
                <i className='fa fa-close' id='close-chat' onClick={closeChatHandler}></i>
            </div>
        </div>
    );
};


export default ChatHeader