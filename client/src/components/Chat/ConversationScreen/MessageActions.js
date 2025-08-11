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

const MessageActions = ({ id, isCurrentUser }) => {

    const dispatch = useDispatch();
    const { reactMessageApi } = useSelector(state => state.activeChatTab) || {};

    const reactMessageHandler = (e) => {
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
                        apiRequest(`${reactMessageApi}/${id}`, 'DELETE').then(({ data, message }) => {
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

    return reactMessageApi && (
        <div className={`message-actions FRCC ${isCurrentUser ? 'sent' : 'received'}`}>
            {isCurrentUser && <i className='fa fa-trash delete-icon' style={{ color: '#ff6c6cff' }} title="Delete" onClick={deleteMessageHandler} />}
            <div className="reactions">
                {reactionsList.map(({ name, icon, color }) => (
                    <i key={name} className={`fa ${icon} w20 reaction-icon`} style={{ color }} title={name} onClick={reactMessageHandler} />
                ))}
            </div>
        </div>
    )
}

export default MessageActions