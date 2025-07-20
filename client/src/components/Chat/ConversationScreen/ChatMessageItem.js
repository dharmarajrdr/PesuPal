import MessageDeleted from './MessageDeleted';
import ReadReceipt from './ReadReceipt';
import Message from './Message';

const ChatMessageItem = ({ msg, currentUserId }) => {
    const formatTime = (iso) => new Date(iso).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    const isCurrentUser = msg.sender == currentUserId;

    return (
        <div className='row w100 FRCS'>
            <div className={`message ${isCurrentUser ? 'sent' : 'received'}`}>
                {msg.deleted ? (
                    <MessageDeleted />
                ) : (
                    <>
                        <div className="message-content">
                            <Message html={msg.message} />
                            <div className="actions">
                                <i className='fa fa-trash delete-icon' title="Delete" />
                                <div className="reactions">
                                    {['LIKE', 'LOVE', 'FUNNY', 'ANGRY', 'DISLIKE'].map((reaction) => (
                                        <i key={reaction} className='fa fa-smile reaction-icon' title={reaction} />
                                    ))}
                                </div>
                            </div>
                        </div>
                        <div className="message-meta FRCE">
                            <span className="time">{formatTime(msg.createdAt)}</span>
                            {isCurrentUser && <ReadReceipt readReceipt={msg.readReceipt} />}
                        </div>
                    </>
                )}
            </div>
        </div>
    );
};

export default ChatMessageItem;