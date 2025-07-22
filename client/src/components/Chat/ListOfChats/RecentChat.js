import './RecentChats.css';
import { StatusIndicator } from '../../Auth/utils';
import { setActiveRecentChat } from '../../../store/reducers/ActiveRecentChatSlice';
import { useDispatch, useSelector } from 'react-redux';
import UserAvatar from '../../User/UserAvatar';

const RecentChat = ({ recentChat, openChatHandler }) => {

    const dispatch = useDispatch();
    const currentChatId = useSelector(state => state.chatId);
    const { chatId, image, name, status, recentMessage } = recentChat;
    const { message, media, createdAt, sender, number_of_unread_messages } = recentMessage;
    const activeChatTab = useSelector(state => state.activeChatTab);

    const isActive = currentChatId == chatId;
    if (isActive) {
        dispatch(setActiveRecentChat(recentChat));
    }

    return (
        <div className={`RecentChatContainer cursP FRCS w100 ${isActive ? 'active' : ''}`} onClick={() => openChatHandler(recentChat)}>
            <div className='pR'>
                <UserAvatar displayPicture={image} displayName={name} />
                {activeChatTab?.showStatusIndicator && <StatusIndicator status={status} />}
            </div>
            <div className='FCSC w100 name_message_date'>
                <div className='FRCB w100'>
                    <span className='name' title={name}>{name}</span>
                    <span className='createdAt'>{createdAt}</span>
                </div>
                <div className='FRCB w100'>
                    <span className='message FRCS'>
                        {sender != name ? <span className='sent_by_me mR5'>{sender}:</span> : ''}
                        {media && <i className='fa-solid fa-image fs10 mR5 color777'></i>}
                        {message}
                    </span>
                    {number_of_unread_messages > 0 && <span className='number_of_unread_messages'>{number_of_unread_messages}</span>}
                </div>
            </div>
        </div>
    )
}

export default RecentChat