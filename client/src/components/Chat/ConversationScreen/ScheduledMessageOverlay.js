import { useState } from 'react';
import './ScheduledMessageOverlay.css';
import Loader from '../../Loader';

const NoMessagesFound = () => {

    return (
        <div className='FCCC w100 h100P' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-calendar-times mR5 w15' />
                No messages scheduled yet
            </p>
        </div>
    )
}

const ScheduledMessagesList = ({ messages }) => {

    return null;
}

const ScheduledMessageOverlay = ({ onClose }) => {

    const [loader, setLoader] = useState(false);
    const [messages, setMessages] = useState([]);

    return (
        <div id='scheduled-message-overlay' className='entire-screen-overlay' onClick={onClose}>
            <div id='schedule-messages-container'>
                <h2 id='scheduled-messages-title'>Scheduled Messages</h2>
                {loader ? <Loader /> :
                    messages.length ? <ScheduledMessagesList messages={messages} /> : <NoMessagesFound />}
            </div>
        </div>
    )
}

export default ScheduledMessageOverlay