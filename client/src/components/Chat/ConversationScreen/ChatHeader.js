import UserAvatar from '../../User/UserAvatar';
import './ChatHeader.css';
import { useNavigate } from 'react-router-dom';

const users = {
    1: { name: 'Dharmaraj' },
    2: { name: 'Rathinavel' },
};

const ChatHeader = ({ conversationInfo, setActiveRecentChat, chatId }) => {

    const navigate = useNavigate();

    const receiver = users[conversationInfo.participants[0].id];

    const closeChatHandler = () => {
        navigate('/chat');
        setActiveRecentChat(chatId);
    }

    return (
        <div className="chat-header FRCB w100">
            <div className='FRCS'>
                <UserAvatar displayPicture={receiver.avatar} displayName={receiver.name} />
                <div className="name">{receiver.name}</div>
            </div>
            <div>
                <i className='fa fa-close' id='close-chat' onClick={closeChatHandler}></i>
            </div>
        </div>
    );
};


export default ChatHeader