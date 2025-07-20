import { useEffect, useRef } from 'react';
import './ChatMessages.css'
import UserAvatar from '../../User/UserAvatar';
import Loader from '../../Loader';

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

/**
 * Greet the user with a message to start a new conversation.
 * @returns 
 */
const StartNewConversation = () => {

    return <div id='start-new-conversation' className='FCCE'>
        <div className='FRCC' id='users-avatars'>
            <UserAvatar displayPicture={"/images/Users/user_8.jpg"} />
            <UserAvatar displayPicture={"/images/Users/user_6.jpg"} />
        </div>
        <button id='say-hello-button' className='mT15'>Say hello <i class="fa fa-hand" id='wave-hands'></i></button>
    </div>
}

const MessageDeleted = () => <p className='message-content message-deleted FRCS selectNone'><i className='fa fa-trash fs12 mR5 colorAAA' title="Deleted" /><span className="deleted-message colorAAA italic fs12">This message was deleted</span></p>

const Message = ({ html }) => <div className="html-content-renderer" dangerouslySetInnerHTML={{ __html: html }} />

const ChatMessages = ({ messages, currentUserId, chatId, retrievingChat }) => {

    const formatDate = (iso) => new Date(iso).toDateString();
    const formatTime = (iso) => new Date(iso).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

    let lastDate = null;

    const chatContainerRef = useRef(null);

    useEffect(() => {
        if (chatContainerRef.current) {
            chatContainerRef.current.scrollTop = chatContainerRef.current.scrollHeight;
        }
    }, [chatId, messages]);

    return (
        <div className="chat-messages" ref={chatContainerRef}>

            {retrievingChat ? <Loader /> : messages.length ? messages.map((msg) => {

                const isCurrentUser = msg.sender === currentUserId;
                const newDate = formatDate(msg.createdAt);
                const showDate = newDate !== lastDate;
                lastDate = newDate;

                return (
                    <div key={msg.id}>
                        {showDate && <div className="date-label">{newDate}</div>}
                        <div className='row w100 FRCS'>
                            <div className={`message ${isCurrentUser ? 'sent' : 'received'}`}>
                                {msg.deleted ? <MessageDeleted /> : <>
                                    <div className="message-content">
                                        <Message html={msg.message} />
                                        <div className="actions">
                                            <i className='fa fa-trash delete-icon' title="Delete" />
                                            <div className="reactions">
                                                {reactionsList.map((reaction) => (
                                                    <i
                                                        key={reaction}
                                                        className={`fa fa-smile reaction-icon`}
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
                                </>}
                            </div>
                        </div>
                    </div>
                );
            }) : <StartNewConversation />}
        </div>
    );
};

export default ChatMessages