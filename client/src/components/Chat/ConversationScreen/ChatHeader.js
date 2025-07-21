import { useEffect, useState } from 'react';
import Profile from '../../OthersProfile/Profile';
import UserAvatar from '../../User/UserAvatar';
import './ChatHeader.css';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { setShowChatHeaderOptionsModal } from '../../../store/reducers/ShowChatHeaderOptionsModalSlice';
import OptionsModal from '../../Utils/OptionsModal';
import { apiRequest } from '../../../http_request';
import { addPinnedDirectMessage, removePinnedDirectMessage } from '../../../store/reducers/PinnedDirectMessageSlice';
import { setCurrentChatPreview } from '../../../store/reducers/CurrentChatPreviewSlice';
import { setActiveRecentChat } from '../../../store/reducers/ActiveRecentChatSlice';

const ChatHeader = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const showChatHeaderOptionsModalSlice = useSelector(state => state.showChatHeaderOptionsModalSlice);
    const [showProfile, setShowProfile] = useState(false);
    const currentChatPreview = useSelector(state => state.currentChatPreviewSlice);
    const [pinnedId, setPinnedIdState] = useState(null);
    const { id: otherUserId, displayName, displayPicture } = currentChatPreview?.otherUser || {};
    const { chatId } = currentChatPreview || {};

    const closeChatHandler = () => {
        dispatch(setActiveRecentChat({}));
        navigate('/chat');
    }

    const chatHeaderOptionsClickHandler = () => {
        dispatch(setShowChatHeaderOptionsModal(!showChatHeaderOptionsModalSlice));
    }

    useEffect(() => {
        setPinnedIdState(currentChatPreview?.pinnedId);
    }, [currentChatPreview]);

    const options = [
        {
            name: `${pinnedId ? 'Unpin' : 'Pin'} Conversation`,
            icon: `fa fa-thumbtack${pinnedId ? '-slash' : ''}`,
            onClick: () => {
                if (pinnedId) {
                    apiRequest(`/api/v1/pinned-direct-messages/pin/${pinnedId}`, 'DELETE').then(() => {
                        dispatch(removePinnedDirectMessage(chatId));
                        dispatch(setShowChatHeaderOptionsModal(false));
                        dispatch(setCurrentChatPreview({ ...currentChatPreview, pinnedId: null }));
                    }).catch(({ message }) => {

                    });
                } else {
                    apiRequest(`/api/v1/pinned-direct-messages/pin`, 'POST', { 'pinnedUserId': otherUserId, 'orderIndex': 1 }).then(({ data }) => {
                        console.log(data);
                        dispatch(addPinnedDirectMessage(data));
                        dispatch(setShowChatHeaderOptionsModal(false));
                        dispatch(setCurrentChatPreview({ ...currentChatPreview, pinnedId: data.id }));
                    }).catch(({ message }) => {

                    });
                }
            }
        },
        { name: 'View Media', icon: 'fa fa-image' }
    ];

    return (
        <div className="chat-header FRCB w100">
            {showProfile && <Profile userId={otherUserId} setShowProfile={setShowProfile} />}
            <div className='FRCS'>
                <UserAvatar displayPicture={displayPicture} displayName={displayName} setShowProfile={setShowProfile} />
                <div className="name">{displayName}</div>
            </div>
            <div className='FRCE'>
                {showChatHeaderOptionsModalSlice && <OptionsModal options={options} />}
                <i className='header-icons fa fa-ellipsis-v' id='chat-header-options' onClick={chatHeaderOptionsClickHandler} />
                <i className='header-icons fa fa-close mL10' id='close-chat' onClick={closeChatHandler}></i>
            </div>
        </div>
    );
};


export default ChatHeader