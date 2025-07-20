import UserAvatar from '../../User/UserAvatar';
import './ChatHeader.css';

const users = {
    1: { name: 'Dharmaraj'  },
    2: { name: 'Rathinavel' },
};

const ChatHeader = ({ conversationInfo }) => {
    const receiver = users[conversationInfo.participants[0].id];
    return (
        <div className="chat-header">
            <UserAvatar displayPicture={receiver.avatar} displayName={receiver.name} />
            <div className="name">{receiver.name}</div>
        </div>
    );
};


export default ChatHeader