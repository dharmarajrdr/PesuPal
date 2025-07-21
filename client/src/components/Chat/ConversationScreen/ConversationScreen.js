import { useEffect, useState } from 'react'
import './ConversationScreen.css';
import ChatHeader from './ChatHeader';
import ChatMessages from './ChatMessages';
import ChatInput from './ChatInput';
import useWebSocket from '../../../WebSocket';
import { useParams } from 'react-router-dom';
import { apiRequest } from '../../../http_request';

const readAllMessages = ({ chatId }) => {
  apiRequest(`/api/v1/direct-messages/${chatId}/read_all`, "PUT").then(() => {
    // TODO: Inform the receiver that messages have been read via WebSocket
  }).catch(({ message }) => {
    console.error(message);
  });
}

const ConversationScreen = ({ activeRecentChatState, currentChatIdState }) => {

  const { chatId } = useParams();
  const [, setCurrentChatId] = currentChatIdState;
  const [activeRecentChat, setActiveRecentChat] = activeRecentChatState;

  setCurrentChatId(chatId);

  const [retrievingChat, setRetrievingChat] = useState(true);
  const [messages, setMessages] = useState([]);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(25);
  const [pivotMessageId, setPivotMessageId] = useState(null);
  const [activeChatPreview, setActiveChatPreview] = useState({});

  const [currentUser, setCurrentUser] = useState(activeChatPreview.currentUser || {});
  const [otherUser, setOtherUser] = useState(activeChatPreview.otherUser || {});

  const { sendMessage } = useWebSocket({
    userId: currentUser.id,
    onPrivateMessage: (msg) => {
      setMessages((prev) => [...prev, {
        "id": 101,
        "createdAt": new Date().toISOString(),
        "sender": msg.senderId,
        "receiver": msg.receiverId,
        "message": msg.message,
        "deleted": false,
        "readReceipt": "READ",
        "reactions": {},
        "directMessageMediaFiles": []
      }]);
    },
    onGroupMessage: (msg) => {
      // setChatLog((prev) => [...prev, msg]);
    },
  });

  const clickSendMessageHandler = ({ message }) => {

    const payload = {
      orgId: sessionStorage.getItem('org-id'),
      chatId: 'room123',
      senderId: currentUser.id,
      receiverId: otherUser.id,
      message,
      isGroupMessage: false,
    };

    sendMessage('/app/chat.sendMessage', payload);
    setMessages((prev) => [...prev, {
      "id": 101,
      "createdAt": new Date().toISOString(),
      "sender": currentUser.id,
      "receiver": otherUser.id,
      "message": message,
      "deleted": false,
      "readReceipt": "SENT",
      "reactions": {},
      "directMessageMediaFiles": []
    }]);
  };

  const getChatPreview = (chatId) => {

    const isFirstLoad = true; // since chatId changed
    const pivot = isFirstLoad ? null : pivotMessageId;

    apiRequest(`/api/v1/direct-messages/preview/${chatId}`, "GET").then(({ data }) => {
      setActiveChatPreview(data);
      setCurrentUser(data.currentUser);
      setOtherUser(data.otherUser);

      apiRequest(`/api/v1/direct-messages/${chatId}?page=${page}&size=${size}${pivot ? `&pivot_message_id=${pivot}` : ''}`, "GET").then(({ data }) => {
        setMessages(data);
        setRetrievingChat(false);

        // Update pivot to the last message’s ID
        if (data.length > 0) {
          setPivotMessageId(data.at(-1)?.id);
        }

      }).catch(({ message }) => {
        console.error(message);
        setRetrievingChat(false);
      });

    }).catch(({ message }) => {
      console.error(message);
      setRetrievingChat(false);
    });

  };

  useEffect(() => {

    setActiveRecentChat(activeRecentChat);
    setPivotMessageId(null); // reset state — this takes effect after render
    setRetrievingChat(true);
    getChatPreview(chatId);
    readAllMessages({ chatId });

  }, [chatId]);

  return activeChatPreview ? (
    <div id='ConversationScreen' className='FCSB'>
      <ChatHeader setCurrentChatId={setCurrentChatId} activeRecentChatState={activeRecentChatState} activeChatPreview={activeChatPreview} />
      <ChatMessages activeChatPreview={activeChatPreview} retrievingChat={retrievingChat} messages={messages} currentUserId={currentUser.id} chatId={chatId} clickSendMessageHandler={clickSendMessageHandler} />
      <ChatInput clickSendMessageHandler={clickSendMessageHandler} />
    </div>
  ) : null;
}

export default ConversationScreen