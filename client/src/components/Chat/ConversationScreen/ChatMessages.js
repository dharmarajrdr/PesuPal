import { useEffect, useRef, useState } from 'react';
import './ChatMessages.css'
import Loader from '../../Loader';
import StartNewConversation from './StartNewConversation';
import ChatMessageItem from './ChatMessageItem';
import { useSelector } from 'react-redux';
import utils from '../../../utils';

const formatDate = (iso) => new Date(iso).toDateString();

const SystemMessage = ({ msg }) => {

    const { message, createdAt } = msg || {};

    return message && (
        <div className="system-message w100 FRCC">
            <span title={utils.convertDateAndTime(createdAt)}>{message}</span>
        </div>
    );
}

const ChatMessages = ({ showStartNewConversation, chatId, retrievingChat, clickSendMessageHandler }) => {

    let lastDate = null;
    let previousMessageSenderId = null;
    let previousMessageType = null;

    const chatContainerRef = useRef(null);
    const currentChatPreview = useSelector(state => state.currentChatPreviewSlice);
    const conversationInRedux = useSelector(state => state.conversation?.messages || []);
    const [messages, setMessages] = useState(conversationInRedux || []);

    const { groupChatConfiguration } = currentChatPreview || {};
    const { deleteMessage: messageDeletable, editMessage: messageEditable, pinMessage: messagePinnable } = groupChatConfiguration || {};

    useEffect(() => {
        setMessages(conversationInRedux || []);
    }, [conversationInRedux]);

    useEffect(() => {
        if (chatContainerRef.current) {
            chatContainerRef.current.scrollTo({
                top: chatContainerRef.current.scrollHeight,
                behavior: 'auto'
            });
        }
    }, [chatId, messages]);

    return (
        <div className="chat-messages FCCS w100" ref={chatContainerRef}>

            <div className="spacer" />

            {retrievingChat ? <Loader /> : messages.length ? messages.map((msg) => {

                const { messageType } = msg || {};
                const newDate = formatDate(msg.createdAt);
                const showDate = newDate !== lastDate;
                lastDate = newDate;

                // compare the msg.sender.id of current and previous message
                const isSameSender = previousMessageSenderId === msg.sender.id && !showDate && previousMessageType === messageType;
                previousMessageSenderId = msg.sender.id;
                previousMessageType = msg.messageType;

                return (
                    <div key={msg.id} className='w100'>
                        {showDate && <div className="date-label">{newDate}</div>}
                        {messageType == 'USER_MESSAGE' && <ChatMessageItem msg={msg} isSameSender={isSameSender} messageDeletable={messageDeletable} messageEditable={messageEditable} messagePinnable={messagePinnable} />}
                        {messageType == 'SYSTEM_MESSAGE' && <SystemMessage msg={msg} />}
                    </div>
                );
            }) : (showStartNewConversation && <StartNewConversation clickSendMessageHandler={clickSendMessageHandler} />)}
        </div>
    );
};

export default ChatMessages