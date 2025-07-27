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
import { setChatId } from '../../../store/reducers/ChatIdSlice';
import GroupMembers from '../Group/GroupMembers';

const ParticipantsCount = ({ count }) => {
    return (
        <div id="participants-count" className='FRCC pY5 pX10 mL10 borderRadius5'>
            <i className="fa fa-users pR5 fs12"></i>
            <span className="count fs14">{count}</span>
        </div>
    );
};

const ChatHeader = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const showChatHeaderOptionsModalSlice = useSelector(state => state.showChatHeaderOptionsModalSlice);
    const [showProfile, setShowProfile] = useState(false);
    const [showGroupMembers, setShowGroupMembers] = useState(false);
    const currentChatPreview = useSelector(state => state.currentChatPreviewSlice);
    const [pinnedId, setPinnedIdState] = useState(null);
    const activeChatTab = useSelector(state => state.activeChatTab);
    const { chatId, displayName, displayPicture, userId, participantsCount } = currentChatPreview || {};

    const closeChatHandler = () => {
        dispatch(setActiveRecentChat(null));
        dispatch(setChatId(null));
        navigate(activeChatTab.name == 'groupMessage' ? '/chat/groups' : '/chat/messages');
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
                    apiRequest(`${activeChatTab.pinnedMessagesApi}/pin/${pinnedId}`, 'DELETE').then(() => {
                        dispatch(removePinnedDirectMessage(chatId));
                        dispatch(setShowChatHeaderOptionsModal(false));
                        dispatch(setCurrentChatPreview({ ...currentChatPreview, pinnedId: null }));
                    }).catch(({ message }) => {

                    });
                } else {
                    const payload = {};
                    if(activeChatTab.name == 'directMessage') {
                        Object.assign(payload, { chatId, 'orderIndex': 1 });
                    } else if(activeChatTab.name == 'groupMessage') {
                        Object.assign(payload, { 'groupId': chatId, 'orderIndex': 1 });
                    }
                    apiRequest(`${activeChatTab.pinnedMessagesApi}/pin`, 'POST', payload).then(({ data }) => {
                        dispatch(addPinnedDirectMessage(data));
                        dispatch(setShowChatHeaderOptionsModal(false));
                        dispatch(setCurrentChatPreview({ ...currentChatPreview, pinnedId: data.id }));
                    }).catch(({ message }) => {

                    });
                }
            }
        },
        {
            name: activeChatTab.name == 'groupMessage' ? 'View Members' : null,
            icon: 'fa fa-users',
            onClick: () => {
                setShowGroupMembers(true);
                dispatch(setShowChatHeaderOptionsModal(false));
            }
        },
        { name: 'View Media', icon: 'fa fa-image' }
    ];

    return (
        <div className="chat-header FRCB w100">
            {showProfile && <Profile userId={userId} setShowProfile={setShowProfile} />}
            {showGroupMembers && <GroupMembers groupId={chatId} setShowGroupMembers={setShowGroupMembers} />}
            <div className='FRCS'>
                <UserAvatar displayPicture={displayPicture} displayName={displayName} setShowProfile={setShowProfile} />
                <p className="name mL10">{displayName}</p>
                {participantsCount && <ParticipantsCount count={participantsCount} />}
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