import ConversationScreen from './ConversationScreen/ConversationScreen'
import ListOfChats from './ListOfChats/ListOfChats';
import './ChatLayout.css'
import { Navigate, Route, Routes } from 'react-router-dom';
import ConversationScreenPlaceholder from './ConversationScreen/ConversationScreenPlaceholder';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { resetActiveChatTab } from '../../store/reducers/ActiveChatTabSlice';

const ChatLayout = () => {

    const dispatch = useDispatch();

    useEffect(() => {
        return () => {
            dispatch(resetActiveChatTab()); // Reset the active chat tab when the component unmounts
        };
    }, []);

    return (
        <div className='Layout FRCS'>
            <ListOfChats />
            <Routes>
                <Route path='/' element={<Navigate to={"/chat/messages"} />} />
                <Route path='/*' element={<Navigate to={"/chat/messages"} />} />
                <Route path='/messages' element={<ConversationScreenPlaceholder activeTabName={'directMessage'} />} />
                <Route path="/messages/:chatId" element={<ConversationScreen activeTabName={'directMessage'} />} />
                <Route path='/groups' element={<ConversationScreenPlaceholder activeTabName={'groupMessage'} />} />
                <Route path='/groups/:chatId' element={<ConversationScreen activeTabName={'groupMessage'} />} />
            </Routes>
        </div>
    )
}

export default ChatLayout