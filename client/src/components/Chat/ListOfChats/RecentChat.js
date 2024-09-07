import React from 'react'
import './RecentChats.css';
import { StatusIndicator } from '../../Auth/utils';

const RecentChat = ({ image, name, status, recent_message }) => {
    const { message, is_media, date_time, sender, number_of_unread_messages } = recent_message;
    return (
        <div className='RecentChatContainer cursP FRCS w100'>
            <div className='pR'>
                <img src={image} />
                <StatusIndicator status={status} />
            </div>
            <div className='FCSC w100 name_message_date'>
                <div className='FRCB w100'>
                    <span className='name'>{name}</span>
                    <span className='date_time'>{date_time}</span>
                </div>
                <div className='FRCB w100'>
                    <span className='message FRCS'>
                        {sender != name ? <span className='sent_by_me mR5'>{sender}:</span> : ''}
                        {is_media && <i className='fa-solid fa-image fs10 mR5 color777'></i>}
                        {message}
                    </span>
                    {number_of_unread_messages > 0 && <span className='number_of_unread_messages'>{number_of_unread_messages}</span>}
                </div>
            </div>
        </div>
    )
}

export default RecentChat