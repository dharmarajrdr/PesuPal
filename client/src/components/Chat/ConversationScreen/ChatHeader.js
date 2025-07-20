import './ChatHeader.css';

const users = {
    1: { name: 'Dharmaraj', avatar: 'D' },
    2: { name: 'MohanKumar', avatar: 'M' },
};

const ChatHeader = ({ conversationInfo }) => {
    const receiver = users[conversationInfo.participants[0].id];
    return (
        <div className="chat-header">
            <div className="avatar">{receiver.avatar}</div>
            <div className="name">{receiver.name}</div>
        </div>
    );
};


export default ChatHeader