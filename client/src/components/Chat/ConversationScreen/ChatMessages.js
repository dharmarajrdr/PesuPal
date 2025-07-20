import './ChatMessages.css'

const reactionsList = ['LIKE', 'LOVE', 'FUNNY', 'ANGRY', 'DISLIKE'];

const ReadReceipt = ({ readReceipt }) => {

    const icons = {
        'PENDING': 'fa fa-clock',
        'SENT': 'fa fa-check',
        'DELIVERED': 'fa fa-check-double',
        'READ': 'fa fa-check-double read'
    }

    return <i className={`${icons[readReceipt]} delivery-status mL5`} title={readReceipt.toLowerCase()} />
}

const Message = ({ html }) => <div className="html-content-renderer" dangerouslySetInnerHTML={{ __html: html }} />

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
                        <div className='row w100 FRCS'>
                            <div className={`message ${isCurrentUser ? 'sent' : 'received'}`}>
                                <div className="message-content">
                                    <Message html={msg.message} />
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
                                <div className="message-meta FRCE">
                                    <span className="time">{formatTime(msg.createdAt)}</span>
                                    {isCurrentUser && <ReadReceipt readReceipt={msg.readReceipt} />}
                                </div>
                            </div>
                        </div>
                    </div>
                );
            })}
        </div>
    );
};

export default ChatMessages