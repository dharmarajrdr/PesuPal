import { useDispatch } from 'react-redux';
import { setActiveRecentChat } from '../../../store/reducers/ActiveRecentChatSlice';
import './ConversationScreenPlaceholder.css';
import { setChatId } from '../../../store/reducers/ChatIdSlice';
import { setActiveChatTab } from '../../../store/reducers/ActiveChatTabSlice';

const ConversationScreenPlaceholder = ({ activeTabName }) => {

    const dispatch = useDispatch();
    dispatch(setActiveRecentChat(null));
    dispatch(setChatId(null));
    dispatch(setActiveChatTab(activeTabName));

    return (
        <div id='conversation-screen-placeholder' className='FCCC h100'>
            <div id='conversation-screen-placeholder-image' >
                <img src='/images/Background/chat.gif' />
            </div>
            <div className='FCCC'>
                <p className='fs14'>Select a chat to start messaging. </p>
            </div>
        </div>
    )
}

export default ConversationScreenPlaceholder