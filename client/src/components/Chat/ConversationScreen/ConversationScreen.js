import { useState } from 'react'
import './ConversationScreen.css';
import ChatHeader from './ChatHeader';
import ChatMessages from './ChatMessages';
import ChatInput from './ChatInput';
import Conversation from './Conversation';
import useWebSocket from '../../../WebSocket';
import { useParams } from 'react-router-dom';

const ConversationScreen = () => {

  const { chatId } = useParams();

  const [conversationInfo, setConversationInfo] = useState({
    'type': 'Direct Message',
    'participants': [
      { 'id': 2, 'name': 'MohanKumar', 'avatar': 'M' }
    ]
  });

  const [messages, setMessages] = useState(Conversation);
  const [message, setMessage] = useState('');

  const currentUserId = sessionStorage.getItem('user-id');
  const receiverId = currentUserId === '1' ? '2' : '1';

  const { sendMessage } = useWebSocket({
    userId: currentUserId,
    onPrivateMessage: (msg) => {
      setMessages((prev) => [...prev, {
        "id": 101,
        "createdAt": "2025-06-10T09:15:03.245000",
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
      "createdAt": "2025-06-10T09:15:03.245000",
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

  return (
    <div id='ConversationScreen' className='FCSB'>
      <ChatHeader conversationInfo={conversationInfo} />
      <ChatMessages messages={messages} currentUserId={currentUserId} />
      <ChatInput onSend={clickSendMessageHandler} setMessage={setMessage} message={message} />
    </div>
  )
}

export default ConversationScreen