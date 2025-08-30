import { useDispatch, useSelector } from "react-redux";
import { hideConfirmationPopup, showConfirmationPopup } from "../../../store/reducers/ConfirmationPopupSlice";
import SchedulePicker from "./SchedulePicker";
import { useState } from "react";
import { apiRequest } from "../../../http_request";
import { showPopup } from "../../../store/reducers/PopupSlice";

const ScheduledMessageActions = ({ id, setActiveMessageRowId, updateMessage, deleteMessage }) => {

    const dispatch = useDispatch();
    const [showPicker, setShowPicker] = useState(false);
    const activeChatTab = useSelector(state => state.activeChatTab);
    const { retrieveConversationApi } = activeChatTab || {};

    const deleteScheduledMessageHandler = () => {
        setActiveMessageRowId(id);
        setShowPicker(false);
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to delete this scheduled message?',
            options: [
                {
                    title: 'Delete',
                    color: 'red',
                    onClick: () => {
                        apiRequest(`${retrieveConversationApi}/schedule/${id}`, "DELETE").then(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            setActiveMessageRowId(null);
                            deleteMessage(id);
                            dispatch(showPopup({ message, type: 'success' }));
                        }).catch(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            dispatch(showPopup({ message, type: 'error' }));
                        });
                    }
                },
                {
                    title: 'Cancel',
                    color: 'gray',
                    onClick: () => {
                        dispatch(hideConfirmationPopup());
                        setActiveMessageRowId(null);
                    }
                }
            ]
        }));
    }

    const onReschedule = (scheduledTime) => {
        setActiveMessageRowId(id);
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to reschedule this message?',
            options: [
                {
                    title: 'Reschedule',
                    color: '#007bff',
                    onClick: () => {
                        apiRequest(`${retrieveConversationApi}/reschedule/${id}`, "PATCH", {
                            'scheduleAt': scheduledTime
                        }).then(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            setActiveMessageRowId(null);
                            dispatch(showPopup({ message, type: 'success' }));
                            updateMessage({ id, 'updatedMessage': { 'createdAt': scheduledTime } });
                        }).catch(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            dispatch(showPopup({ message, type: 'error' }));
                        });
                    }
                },
                {
                    title: 'Cancel',
                    color: 'gray',
                    onClick: () => {
                        dispatch(hideConfirmationPopup());
                        setActiveMessageRowId(null);
                    }
                }
            ]
        }));
    }

    const rescheduleScheduledMessageHandler = () => {
        setActiveMessageRowId(showPicker ? null : id);
        setShowPicker(!showPicker);
    }

    const sendScheduledMessageHandler = () => {
        setActiveMessageRowId(id);
        setShowPicker(false);
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to send this scheduled message?',
            options: [
                {
                    title: 'Send',
                    color: 'green',
                    onClick: () => {
                        apiRequest(`${retrieveConversationApi}/unschedule/${id}`, "PATCH").then(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            setActiveMessageRowId(null);
                            dispatch(showPopup({ message, type: 'success' }));
                            deleteMessage(id);
                        }).catch(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            dispatch(showPopup({ message, type: 'error' }));
                        });
                    }
                },
                {
                    title: 'Cancel',
                    color: 'gray',
                    onClick: () => {
                        dispatch(hideConfirmationPopup());
                        setActiveMessageRowId(null);
                    }
                }
            ]
        }));
    }

    return (
        <div className="scheduled-message-actions message-actions FRCC sent">
            <SchedulePicker onSchedule={onReschedule} showPicker={showPicker} setShowPicker={setShowPicker} onClose={() => setShowPicker(false)} onCancel={() => setActiveMessageRowId(null)} />
            <i className='fa fa-paper-plane send-icon' title="Send Now" style={{ color: '#007bff' }} onClick={sendScheduledMessageHandler} />
            <i className='fa fa-clock-rotate-left send-icon' title="Reschedule" style={{ color: '#d36d00ff' }} onClick={rescheduleScheduledMessageHandler} />
            <i className='fa fa-trash delete-icon' title="Delete" style={{ color: '#ff6c6cff' }} onClick={deleteScheduledMessageHandler} />
        </div>
    )
}

export default ScheduledMessageActions;