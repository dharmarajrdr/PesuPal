import ConversationScreen from './ConversationScreen/ConversationScreen'
import ListOfChats from './ListOfChats/ListOfChats';
import './ChatLayout.css'
import { Route, Routes } from 'react-router-dom';
import ConversationScreenPlaceholder from './ConversationScreen/ConversationScreenPlaceholder';
import { useState } from 'react';

const ChatLayout = () => {

    const currentChatIdState = useState(null);

    return (
        <div className='Layout FRCS'>
            <ListOfChats currentChatIdState={currentChatIdState} />
            <Routes>
                <Route path='/' element={<ConversationScreenPlaceholder setCurrentChatId={currentChatIdState[1]} />} />
                <Route path="/:chatId" element={<ConversationScreen currentChatIdState={currentChatIdState} />} />
            </Routes>
        </div>
    )
}

export default ChatLayout