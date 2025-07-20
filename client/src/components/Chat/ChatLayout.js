import ConversationScreen from './ConversationScreen/ConversationScreen'
import ListOfChats from './ListOfChats/ListOfChats';
import './ChatLayout.css'
import { Route, Routes } from 'react-router-dom';
import ConversationScreenPlaceholder from './ConversationScreen/ConversationScreenPlaceholder';
import { useState } from 'react';

const ChatLayout = () => {
    
    const activeRecentChatState = useState(null);
    const currentChatIdState = useState(null);

    return (
        <div className='Layout FRCS'>
            <ListOfChats activeRecentChatState={activeRecentChatState} currentChatIdState={currentChatIdState} />
            <Routes>
                <Route path='/' element={<ConversationScreenPlaceholder activeRecentChatState={activeRecentChatState} />} />
                <Route path="/:chatId" element={<ConversationScreen activeRecentChatState={activeRecentChatState} currentChatIdState={currentChatIdState} />} />
            </Routes>
        </div>
    )
}

export default ChatLayout