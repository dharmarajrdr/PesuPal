import ConversationScreen from './ConversationScreen/ConversationScreen'
import ListOfChats from './ListOfChats/ListOfChats';
import './ChatLayout.css'
import { Route, Routes } from 'react-router-dom';
import ConversationScreenPlaceholder from './ConversationScreen/ConversationScreenPlaceholder';
import { useState } from 'react';

const ChatLayout = () => {
    const activeRecentChat = useState(null);
    return (
        <div className='Layout FRCS'>
            <ListOfChats activeRecentChat={activeRecentChat} />
            <Routes>
                <Route path='/' element={<ConversationScreenPlaceholder activeRecentChat={activeRecentChat} />} />
                <Route path="/:chatId" element={<ConversationScreen setActiveRecentChat={activeRecentChat[1]} />} />
            </Routes>
        </div>
    )
}

export default ChatLayout