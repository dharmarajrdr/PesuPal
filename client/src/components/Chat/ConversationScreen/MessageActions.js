import { useDispatch, useSelector } from "react-redux";
import { apiRequest } from "../../../http_request";
import { showPopup } from "../../../store/reducers/PopupSlice";
import { deleteMessage, reactMessage } from "../../../store/reducers/ConversationSlice";
import { hideConfirmationPopup, showConfirmationPopup } from "../../../store/reducers/ConfirmationPopupSlice";

const reactionsList = [
    {
        "name": 'LIKE',
        "icon": 'fa-thumbs-up',
        "color": '#00ac00ff'
    },
    {
        "name": 'DISLIKE',
        "icon": 'fa-thumbs-down',
        "color": '#ff6c6cff'
    },
    {
        "name": 'LOVE',
        "icon": 'fa-heart',
        "color": '#ff65ffff'
    },
    {
        "name": 'FUNNY',
        "icon": 'fa-laugh',
        "color": '#b2a000ff'
    },
    {
        "name": 'ANGRY',
        "icon": 'fa-angry',
        "color": '#ff4000ff'
    }
];

const MessageActions = ({ id, isCurrentUser, messageDeletable, messageEditable, messagePinnable }) => {

    messageEditable = messageEditable == undefined ? true : messageEditable;
    messagePinnable = messagePinnable == undefined ? true : messagePinnable;
    messageDeletable = messageDeletable == undefined ? true : messageDeletable;

    const dispatch = useDispatch();
    const { reactMessageApi, deleteMessageApi, chatMode } = useSelector(state => state.activeChatTab) || {};

    const reactMessageHandler = (e) => {
        if (!reactMessageApi) {
            return dispatch(showPopup({ message: 'Feature not implemented yet.', type: 'error' }));
        }
        const reaction = e.target.getAttribute('title');
        e.stopPropagation();
        e.preventDefault();
        apiRequest(`${reactMessageApi}/${id}/react`, 'POST', { reaction }).then(({ data, message }) => {
            dispatch(reactMessage({ id, reaction }));
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    }

    const deleteMessageHandler = () => {
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to delete this message?',
            options: [
                {
                    title: 'Delete',
                    color: 'red',
                    onClick: () => {
                        apiRequest(`${deleteMessageApi}/${id}`, 'DELETE').then(({ data, message }) => {
                            dispatch(deleteMessage({ id }));
                            dispatch(showPopup({ message, type: 'success' }));
                            dispatch(hideConfirmationPopup());
                        }).catch(({ message }) => {
                            dispatch(showPopup({ message, type: 'error' }));
                            dispatch(hideConfirmationPopup());
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
        <div className={`message-actions FRCC ${isCurrentUser ? 'sent' : 'received'}`}>
            <i className="fa fa-reply reply-icon" style={{ color: '#00aaff' }} title="Reply" />
            <i className="fa fa-share forward-icon" style={{ color: '#ffb300' }} title="Forward" />
            {messagePinnable && <i className="fa fa-thumbtack pin-icon" style={{ color: '#ff6c6cff' }} title="Pin" />}
            {isCurrentUser ? <>
                {chatMode == 'GROUP_MESSAGE' && <i className='fa fa-eye delete-icon' style={{ color: '#23a9a0' }} title="Views" />}
                {messageEditable && <i className='fa fa-edit delete-icon' style={{ color: '#b02da3ff' }} title="Edit" />}
                {messageDeletable && <i className='fa fa-trash delete-icon' style={{ color: '#ff6c6cff' }} title="Delete" onClick={deleteMessageHandler} />}
            </> : <div className="reactions">
                {reactionsList.map(({ name, icon, color }) => (
                    <i key={name} className={`fa ${icon} reaction-icon`} style={{ color }} title={name} onClick={reactMessageHandler} />
                ))}
            </div>}
        </div>
    )
}

export default MessageActions