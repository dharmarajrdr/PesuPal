import { useState } from 'react'
import './ConversationScreen.css';
import ChatHeader from './ChatHeader';
import ChatMessages from './ChatMessages';
import ChatInput from './ChatInput';
import Conversation from './Conversation';

const ConversationScreen = () => {

  const [conversationInfo, setConversationInfo] = useState({
    'type': 'Direct Message',
    'participants': [
      { 'id': 2, 'name': 'MohanKumar', 'avatar': 'M' }
    ]
  });
  const [messages, setMessages] = useState(Conversation);
  return (
    <div id='ConversationScreen'>
      <ChatHeader conversationInfo={conversationInfo} />
      <ChatMessages messages={messages} />
      <ChatInput />
    </div>
  )
}

export default ConversationScreen