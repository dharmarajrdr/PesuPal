import { useEffect, useRef } from 'react';
import './ChatMessages.css'
import Loader from '../../Loader';
import MessageDeleted from './MessageDeleted';
import Message from './Message';
import StartNewConversation from './StartNewConversation';
import MessageActions from './MessageActions';
import MessageMeta from './MessageMeta';

const ChatMessages = ({ messages, currentUserId, chatId, retrievingChat }) => {

    const formatDate = (iso) => new Date(iso).toDateString();

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
                                        <Message html={msg.id} />
                                        <MessageActions />
                                    </div>
                                    <MessageMeta createdAt={msg.createdAt} readReceipt={msg.readReceipt} isCurrentUser={isCurrentUser} />
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