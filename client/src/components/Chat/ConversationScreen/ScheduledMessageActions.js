import { useDispatch } from "react-redux";
import { hideConfirmationPopup, showConfirmationPopup } from "../../../store/reducers/ConfirmationPopupSlice";

const ScheduledMessageActions = ({ id }) => {

    const dispatch = useDispatch();

    const deleteScheduledMessageHandler = () => {
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to delete this scheduled message?',
            options: [
                {
                    title: 'Delete',
                    color: 'red',
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

    const sendScheduledMessageHandler = () => {
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to send this scheduled message?',
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

    return (
        <div className="scheduled-message-actions message-actions FRCC sent">
            <i className='fa fa-paper-plane send-icon' title="Send Now" style={{ color: '#007bff' }} onClick={sendScheduledMessageHandler} />
            <i className='fa fa-trash delete-icon' title="Delete" style={{ color: '#ff6c6cff' }} onClick={deleteScheduledMessageHandler} />
        </div>
    )
}

export default ScheduledMessageActions;