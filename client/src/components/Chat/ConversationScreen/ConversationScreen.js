import { useEffect, useState } from 'react'
import './ConversationScreen.css';
import ChatHeader from './ChatHeader';
import ChatMessages from './ChatMessages';
import ChatInput from './ChatInput';
import useWebSocket from '../../../WebSocket';
import { useParams } from 'react-router-dom';
import { apiRequest } from '../../../http_request';

const ConversationScreen = ({ setActiveRecentChat }) => {

  const { chatId } = useParams();

  const [conversationInfo, setConversationInfo] = useState({
    'type': 'Direct Message',
    'participants': [
      { 'id': 2, 'name': 'MohanKumar', 'avatar': 'M' }
    ]
  });

  const [retrievingChat, setRetrievingChat] = useState(true);
  const [messages, setMessages] = useState([]);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(25);
  const [pivotMessageId, setPivotMessageId] = useState(null);

  const [message, setMessage] = useState('');

  const currentUserId = sessionStorage.getItem('user-id');
  const receiverId = currentUserId === '1' ? '2' : '1';

  const { sendMessage } = useWebSocket({
    userId: currentUserId,
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

  const clickSendMessageHandler = () => {
    const payload = {
      orgId: sessionStorage.getItem('org-id'),
      chatId: 'room123',
      senderId: currentUserId,
      receiverId: receiverId,
      message,
      isGroupMessage: false,
    };
    sendMessage('/app/chat.sendMessage', payload);
    setMessages((prev) => [...prev, {
      "id": 101,
      "createdAt": new Date().toISOString(),
      "sender": currentUserId,
      "receiver": receiverId,
      "message": message,
      "deleted": false,
      "readReceipt": "SENT",
      "reactions": {},
      "directMessageMediaFiles": []
    }]);
    setMessage('');
  };

  useEffect(() => {

    const isFirstLoad = true; // since chatId changed
    const pivot = isFirstLoad ? null : pivotMessageId;

    setPivotMessageId(null); // reset state — this takes effect after render

    setRetrievingChat(true);
    setActiveRecentChat(chatId);

    const url = `/api/v1/direct-messages/${chatId}?page=${page}&size=${size}` + (pivot ? `&pivot_message_id=${pivot}` : '');

    apiRequest(url, "GET").then(({ data }) => {
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
  }, [chatId]);


  return (
    <div id='ConversationScreen' className='FCSB'>
      <ChatHeader conversationInfo={conversationInfo} setActiveRecentChat={setActiveRecentChat} chatId={chatId} />
      <ChatMessages retrievingChat={retrievingChat} messages={messages} currentUserId={currentUserId} chatId={chatId} />
      <ChatInput clickSendMessageHandler={clickSendMessageHandler} setMessage={setMessage} message={message} />
    </div>
  )
}

export default ConversationScreen