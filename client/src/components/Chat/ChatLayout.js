import ConversationScreen from './ConversationScreen/ConversationScreen'
import ListOfChats from './ListOfChats/ListOfChats';
import './ChatLayout.css'
import { Navigate, Route, Routes } from 'react-router-dom';
import ConversationScreenPlaceholder from './ConversationScreen/ConversationScreenPlaceholder';

const ChatLayout = () => {

    return (
        <div className='Layout FRCS'>
            <ListOfChats />
            <Routes>
                <Route path='/' element={<Navigate to={"/chat/messages"} />} />
                <Route path='/*' element={<Navigate to={"/chat/messages"} />} />
                <Route path='/messages' element={<ConversationScreenPlaceholder />} />
                <Route path="/messages/:chatId" element={<ConversationScreen />} />
            </Routes>
        </div>
    )
}

export default ChatLayout