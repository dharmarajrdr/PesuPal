import { useDispatch } from 'react-redux';
import { setActiveRecentChat } from '../../../store/reducers/ActiveRecentChatSlice';
import './ConversationScreenPlaceholder.css';

const ConversationScreenPlaceholder = ({ setCurrentChatId }) => {

    const dispatch = useDispatch();
    setCurrentChatId(null);
    dispatch(setActiveRecentChat(null));

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