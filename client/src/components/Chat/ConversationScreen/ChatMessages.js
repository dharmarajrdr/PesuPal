import { useEffect, useRef } from 'react';
import './ChatMessages.css'
import Loader from '../../Loader';
import StartNewConversation from './StartNewConversation';
import ChatMessageItem from './ChatMessageItem';

const ChatMessages = ({ messages, showStartNewConversation, chatId, retrievingChat, clickSendMessageHandler }) => {

    const formatDate = (iso) => new Date(iso).toDateString();

    let lastDate = null;
    let previousMessageSenderId = null;

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

                const newDate = formatDate(msg.createdAt);
                const showDate = newDate !== lastDate;
                lastDate = newDate;

                // compare the msg.sender.id of current and previous message
                const isSameSender = previousMessageSenderId === msg.sender.id && !showDate;
                previousMessageSenderId = msg.sender.id;

                return (
                    <div key={msg.id} className='w100'>
                        {showDate && <div className="date-label">{newDate}</div>}
                        <ChatMessageItem msg={msg} isSameSender={isSameSender} />
                    </div>
                );
            }) : (showStartNewConversation && <StartNewConversation clickSendMessageHandler={clickSendMessageHandler} />)}
        </div>
    );
};

export default ChatMessages