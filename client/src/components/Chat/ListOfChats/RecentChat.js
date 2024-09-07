import React from 'react'
import './RecentChats.css';

const RecentChat = ({ image, name, recent_message }) => {
    const { message, is_media, date_time, sender } = recent_message;
    return (
        <div className='RecentChatContainer cursP FRCS w100'>
            <img src={image} />
            <div className='FCSC w100 name_message_date'>
                <div className='FRCB w100'>
                    <span className='name'>{name}</span>
                    <span className='date_time'>{date_time}</span>
                </div>
                <span className='message'>
                    {sender != name ? <span className='sent_by_me'>{sender}: </span> : ''}
                    {message}
                </span>
            </div>
        </div>
    )
}

export default RecentChat