import { useEffect, useRef } from 'react';
import './ChatMessages.css'
import Loader from '../../Loader';
import MessageDeleted from './MessageDeleted';
import Message from './Message';
import ReadReceipt from './ReadReceipt';
import StartNewConversation from './StartNewConversation';

const reactionsList = ['LIKE', 'LOVE', 'FUNNY', 'ANGRY', 'DISLIKE'];

const ChatMessages = ({ messages, currentUserId, chatId, retrievingChat }) => {

    const formatDate = (iso) => new Date(iso).toDateString();
    const formatTime = (iso) => new Date(iso).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

    let lastDate = null;

    const chatContainerRef = useRef(null);

    useEffect(() => {
        if (chatContainerRef.current) {
            chatContainerRef.current.scrollTo({
                top: chatContainerRef.current.scrollHeight,
                behavior: 'auto' // or 'smooth'
            });
        }
    }, [chatId, messages]);

    return (
        <div className="chat-messages FCCS w100" ref={chatContainerRef}>

            <div className="spacer" />

            {retrievingChat ? <Loader /> : messages.length ? messages.map((msg) => {

                const isCurrentUser = msg.sender == currentUserId;
                const newDate = formatDate(msg.createdAt);
                const showDate = newDate !== lastDate;
                lastDate = newDate;

                return (
                    <div key={msg.id} className='w100'>
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
                                                    <i key={reaction} className={`fa fa-smile reaction-icon`} title={reaction} />
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