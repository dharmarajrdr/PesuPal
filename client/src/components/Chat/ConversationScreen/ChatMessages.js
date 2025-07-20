import './ChatMessages.css'

const reactionsList = ['LIKE', 'LOVE', 'FUNNY', 'ANGRY', 'DISLIKE'];

const ChatMessages = ({ messages, currentUserId }) => {
    const formatDate = (iso) => new Date(iso).toDateString();
    const formatTime = (iso) => new Date(iso).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

    let lastDate = null;

    return (
        <div className="chat-messages">
            {messages.map((msg) => {
                const isCurrentUser = msg.sender === currentUserId;
                const newDate = formatDate(msg.createdAt);
                const showDate = newDate !== lastDate;
                lastDate = newDate;

                return (
                    <div key={msg.id}>
                        {showDate && <div className="date-label">{newDate}</div>}
                        <div className={`message ${isCurrentUser ? 'sent' : 'received'}`}>
                            <div className="message-content">
                                <span>{msg.message}</span>
                                <div className="actions">
                                    <i className='fa fa-trash delete-icon' title="Delete" />
                                    <div className="reactions">
                                        {reactionsList.map((reaction) => (
                                            <i
                                                key={reaction}
                                                className={`fa fa-smile-o reaction-icon`}
                                                title={reaction}
                                            />
                                        ))}
                                    </div>
                                </div>
                            </div>
                            <div className="message-meta">
                                <span className="time">{formatTime(msg.createdAt)}</span>
                            </div>
                        </div>
                    </div>
                );
            })}
        </div>
    );
};

export default ChatMessages