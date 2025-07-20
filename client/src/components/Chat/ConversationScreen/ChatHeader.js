import UserAvatar from '../../User/UserAvatar';
import './ChatHeader.css';
import { useNavigate } from 'react-router-dom';

const ChatHeader = ({ activeRecentChatState }) => {

    const navigate = useNavigate();
    const [activeRecentChat, setActiveRecentChat] = activeRecentChatState;
    const { name, image } = activeRecentChat || {};

    const closeChatHandler = () => {
        navigate('/chat');
        setActiveRecentChat(null);
    }

    return (
        <div className="chat-header FRCB w100">
            <div className='FRCS'>
                <UserAvatar displayPicture={image} displayName={name} />
                <div className="name">{name}</div>
            </div>
            <div>
                <i className='fa fa-close' id='close-chat' onClick={closeChatHandler}></i>
            </div>
        </div>
    );
};


export default ChatHeader