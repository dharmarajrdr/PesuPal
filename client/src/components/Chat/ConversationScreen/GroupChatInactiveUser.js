import { useDispatch, useSelector } from 'react-redux';
import './ChatInputUserArchived.css';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { apiRequest } from '../../../http_request';
import { updateCurrentChatPreview } from '../../../store/reducers/CurrentChatPreviewSlice';

const GroupChatInactiveUser = () => {

    const dispatch = useDispatch();
    const currentChatPreview = useSelector(state => state.currentChatPreviewSlice) || {};
    const { chatId: groupId } = currentChatPreview || {};

    const joinGroupHandler = () => {
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to join this group?',
            options: [
                {
                    "title": "Yes",
                    "color": "#00a434ff",
                    "onClick": () => {
                        apiRequest(`/api/v1/group-chat-member/join/${groupId}`, 'POST').then(({ message }) => {
                            dispatch(updateCurrentChatPreview({ active: true }));
                            dispatch(hideConfirmationPopup());
                            dispatch(showPopup({ message, type: 'success' }));
                        }).catch(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            dispatch(showPopup({ message, type: 'error' }));
                        });
                    }
                },
                {
                    "title": "No",
                    "color": "#ff0000ff",
                    "onClick": () => dispatch(hideConfirmationPopup())
                }
            ]
        }))
    }

    return (
        <div id='chat-input-user-archived' className='w100 FRCC p20'>
            <i className="fa-solid fa-user-slash mR5"></i>
            <p>
                <span>You are not a part of this conversation.</span>
                <span className='mL5'><span id='reopen-group-button' onClick={joinGroupHandler}>Join</span> now</span>
            </p>
        </div>
    )
}

export default GroupChatInactiveUser