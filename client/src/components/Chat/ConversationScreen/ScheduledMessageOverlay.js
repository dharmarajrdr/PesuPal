import { useEffect, useState } from 'react';
import './ScheduledMessageOverlay.css';
import Loader from '../../Loader';
import { apiRequest } from '../../../http_request';
import { useDispatch, useSelector } from 'react-redux';
import { showPopup } from '../../../store/reducers/PopupSlice';
import ChatMessageItem from './ChatMessageItem';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';

const formatDate = (iso) => new Date(iso).toDateString();

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

const ScheduledMessagesList = ({ messages, setMessages }) => {

    let lastDate = null;
    const [activeMessageRowId, setActiveMessageRowId] = useState(null);

    const updateMessage = ({ id, updatedMessage }) => {

        setMessages(prevMessages => prevMessages.map(msg => msg.id === id ? { ...msg, ...updatedMessage } : msg));
        setMessages(prevMessages => [...prevMessages].sort((a, b) => new Date(a.createdAt) - new Date(b.createdAt)));
    }

    const deleteMessage = (id) => {

        setMessages(prevMessages => prevMessages.filter(msg => msg.id !== id));
    }

    return <div className='w100 h100P FCSE chat-messages' id='scheduled-messages-list'>

        {messages.length ? messages.map((message) => {

            const { createdAt, id } = message || {};
            const newDate = formatDate(createdAt);
            const showDate = newDate !== lastDate;
            lastDate = newDate;

            return (
                <div key={id} className='w100'>
                    {showDate && <div className="date-label">{newDate}</div>}
                    <ChatMessageItem msg={message} isSameSender={!showDate} messageDeletable={true} isScheduledMessage={true} setActiveMessageRowId={setActiveMessageRowId} activeMessageRowId={activeMessageRowId} updateMessage={updateMessage} deleteMessage={deleteMessage} />
                </div>
            );
        }) : <NoMessagesFound />}
    </div>
}

const ScheduledMessageOverlay = ({ onClose, chatId }) => {

    const dispatch = useDispatch();
    const [loader, setLoader] = useState(true);
    const [messages, setMessages] = useState([]);
    const activeChatTab = useSelector(state => state.activeChatTab);
    const { retrieveConversationApi } = activeChatTab || {};

    useEffect(() => {
        apiRequest(`${retrieveConversationApi}/${chatId}/scheduled-messages`, 'GET').then(({ data }) => {
            setMessages(data);
            setLoader(false);
        }).catch(({ message }) => {
            setLoader(false);
            dispatch(showPopup({ message, type: 'error' }));
        });
    }, []);

    const sendAllScheduledMessagesHandler = () => {
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to send all scheduled messages?',
            options: [
                {
                    title: 'Send',
                    color: 'green',
                    onClick: () => {
                        dispatch(hideConfirmationPopup())
                    }
                },
                {
                    title: 'Cancel',
                    color: 'gray',
                    onClick: () => dispatch(hideConfirmationPopup())
                }
            ]
        }));
    }

    const deleteAllScheduledMessagesHandler = () => {
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to delete all scheduled messages?',
            options: [
                {
                    title: 'Delete',
                    color: 'red',
                    onClick: () => {
                        apiRequest(`${retrieveConversationApi}/schedule/all/${chatId}`, "DELETE").then(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            dispatch(showPopup({ message, type: 'success' }));
                            setMessages([]);
                        }).catch(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            dispatch(showPopup({ message, type: 'error' }));
                        });
                    }
                },
                {
                    title: 'Cancel',
                    color: 'gray',
                    onClick: () => dispatch(hideConfirmationPopup())
                }
            ]
        }));
    }

    return (
        <div id='scheduled-message-overlay' className='entire-screen-overlay' onClick={(e) => {
            if (e.target.id === 'scheduled-message-overlay') {
                onClose();
            }
        }}>
            <div id='schedule-messages-container' className='FCCS w100'>
                <div className='FRCB w100' id='scheduled-messages-header'>
                    <h2 id='scheduled-messages-title'>Scheduled Messages {messages.length > 0 ? `(${messages.length})` : null}</h2>
                    {messages.length > 0 && <div className='FRCE'>
                        <button id='send-all-messages' onClick={sendAllScheduledMessagesHandler}>
                            <i className='fa fa-paper-plane mR5 colorFFF' />Send All
                        </button>
                        <button id='delete-all-messages' className='mL10' onClick={deleteAllScheduledMessagesHandler}>
                            <i className='fa fa-trash mR5 colorFFF' />Delete All
                        </button>
                    </div>}
                </div>
                {loader ? <Loader /> : <ScheduledMessagesList messages={messages} setMessages={setMessages} />}
            </div>
        </div>
    )
}

export default ScheduledMessageOverlay