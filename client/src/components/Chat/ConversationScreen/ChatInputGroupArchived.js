import './ChatInputUserArchived.css';
import { useDispatch, useSelector } from 'react-redux';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';
import { apiRequest } from '../../../http_request';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { updateCurrentChatPreview } from '../../../store/reducers/CurrentChatPreviewSlice';

const ChatInputGroupArchived = () => {

    const dispatch = useDispatch();
    const currentChatPreview = useSelector(state => state.currentChatPreviewSlice) || {};
    const { chatId: groupId, reopenable } = currentChatPreview || {};

    const reopenGroupHandler = () => {
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to reopen this group?',
            options: [
                {
                    "title": "Yes",
                    "color": "#00a434ff",
                    "onClick": () => {
                        apiRequest(`/api/v1/group/${groupId}/reopen`, 'PUT').then(({ message }) => {
                            dispatch(updateCurrentChatPreview({ groupActive: true }));
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
                <span>This group is no longer active.</span>
                {reopenable && <span className='mL5'>want to <span id='reopen-group-button' onClick={reopenGroupHandler}>reopen</span>?</span>}
            </p>
        </div>
    )
}

export default ChatInputGroupArchived