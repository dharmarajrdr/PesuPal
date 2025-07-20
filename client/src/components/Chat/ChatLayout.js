import ConversationScreen from './ConversationScreen/ConversationScreen'
import ListOfChats from './ListOfChats/ListOfChats';
import './ChatLayout.css'
import { Route, Routes } from 'react-router-dom';
import ConversationScreenPlaceholder from './ConversationScreen/ConversationScreenPlaceholder';

const ChatLayout = () => {
    return (
        <div className='Layout FRCS'>
            <ListOfChats />
            <Routes>
                <Route path='/' element={<ConversationScreenPlaceholder />} />
                <Route path="/:chatId" element={<ConversationScreen />} />
            </Routes>
        </div>
    )
}

export default ChatLayout