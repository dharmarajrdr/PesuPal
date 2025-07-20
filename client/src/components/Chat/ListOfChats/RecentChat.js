import './RecentChats.css';
import { StatusIndicator } from '../../Auth/utils';
import { useNavigate } from 'react-router-dom';

const RecentChat = ({ recentChat }) => {

    const navigate = useNavigate();
    const { chatId, image, name, status, recentMessage } = recentChat;
    const { message, media, createdAt, sender, number_of_unread_messages } = recentMessage;
    const openChatHandler = () => {
        navigate(`/chat/${chatId}`);
    }
    return (
        <div className='RecentChatContainer cursP FRCS w100' onClick={openChatHandler}>
            <div className='pR'>
                {image ? <img src={image} /> : <i className='fa fa-user-circle fs20' aria-hidden='true'></i>}
                <StatusIndicator status={status} />
            </div>
            <div className='FCSC w100 name_message_date'>
                <div className='FRCB w100'>
                    <span className='name'>{name}</span>
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