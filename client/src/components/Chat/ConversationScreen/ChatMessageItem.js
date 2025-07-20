import MessageDeleted from './MessageDeleted';
import Message from './Message';
import MessageActions from './MessageActions';
import MessageMeta from './MessageMeta';

const ChatMessageItem = ({ msg, currentUserId }) => {

    const isCurrentUser = msg.sender == currentUserId;

    return (
        <div className='row w100 FRCS'>
            <div className={`message ${isCurrentUser ? 'sent' : 'received'}`}>
                {msg.deleted ? <MessageDeleted /> : <>
                    <div className="message-content">
                        <Message html={msg.message} />
                        <MessageActions />
                    </div>
                    <MessageMeta createdAt={msg.createdAt} readReceipt={msg.readReceipt} isCurrentUser={isCurrentUser} />
                </>}
            </div>
        </div>
    );
};

export default ChatMessageItem;