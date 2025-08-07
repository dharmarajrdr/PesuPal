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
import { setCurrentChatPreview, updateCurrentChatPreview } from '../../../store/reducers/CurrentChatPreviewSlice';
import { setActiveRecentChat } from '../../../store/reducers/ActiveRecentChatSlice';
import { setChatId } from '../../../store/reducers/ChatIdSlice';
import GroupMembers from '../Group/GroupMembers';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { clearMessages } from '../../../store/reducers/ConversationSlice';

const ParticipantsCount = ({ count, setShowGroupMembers }) => {
    return (
        <div id="participants-count" className='FRCC pY5 pX10 mL10 borderRadius5' onClick={() => setShowGroupMembers(true)}>
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
    const { chatId, displayName, displayPicture, userId, participantsCount, groupActive, active } = currentChatPreview || {};

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

    const deleteGroupHandler = () => {

        const removeAccessToChatHeaderOptions = () => {
            dispatch(setShowChatHeaderOptionsModal(false));
        }

        const removeAccessToSendFurtherMessages = () => {
            dispatch(updateCurrentChatPreview({ groupActive: false }));
        }

        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to delete this group?',
            options: [
                {
                    title: 'Delete',
                    color: 'red',
                    onClick: () => {
                        apiRequest(`/api/v1/group/${chatId}`, 'DELETE').then(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            dispatch(showPopup({ message, type: 'success' }));
                            removeAccessToChatHeaderOptions();
                            removeAccessToSendFurtherMessages();
                        }).catch(({ message }) => {
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
    };

    const clearChatHandler = () => {

        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to clear this chat?',
            options: [
                {
                    title: 'Clear',
                    color: 'red',
                    onClick: () => {
                        apiRequest(`/api/v1/group-chat-message/clear/${chatId}`, 'DELETE').then(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            dispatch(showPopup({ message, type: 'success' }));
                            dispatch(clearMessages());
                        }).catch(({ message }) => {
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
    };

    const leaveGroupHandler = () => {

        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to leave this group?',
            options: [
                {
                    title: 'Leave',
                    color: 'red',
                    onClick: () => {
                        apiRequest(`/api/v1/group-chat-member/leave/${chatId}`, 'DELETE').then(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            dispatch(showPopup({ message, type: 'success' }));
                            dispatch(updateCurrentChatPreview({ active: false }));
                        }).catch(({ message }) => {
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
    };

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
                    if (activeChatTab.name == 'directMessage') {
                        Object.assign(payload, { chatId, 'orderIndex': 1 });
                    } else if (activeChatTab.name == 'groupMessage') {
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
            name: activeChatTab.name == 'groupMessage' && active && groupActive ? 'Group Info' : null,
            icon: 'fa fa-info-circle',
            onClick: () => {

                dispatch(setShowChatHeaderOptionsModal(false));
            }
        },
        {
            name: activeChatTab.name == 'groupMessage' && active && groupActive ? 'Add Participant' : null,
            icon: 'fa fa-user-plus',
            onClick: () => {
                dispatch(setShowChatHeaderOptionsModal(false));
            }
        },
        {
            name: activeChatTab.name == 'groupMessage' && active && groupActive ? 'Permissions' : null,
            icon: 'fa fa-lock',
            onClick: () => {
                dispatch(setShowChatHeaderOptionsModal(false));
            }
        },
        { name: 'View Media', icon: 'fa fa-image' },
        {
            name: activeChatTab.name == 'groupMessage' && active && groupActive ? 'Clear Chat' : null,
            icon: 'fa fa-delete-left',
            onClick: () => {
                clearChatHandler();
                dispatch(setShowChatHeaderOptionsModal(false));
            }
        },
        {
            name: activeChatTab.name == 'groupMessage' && active && groupActive ? 'Leave Group' : null,
            icon: 'fa fa-sign-out-alt',
            onClick: () => {
                leaveGroupHandler();
                dispatch(setShowChatHeaderOptionsModal(false));
            }
        },
        {
            name: activeChatTab.name == 'groupMessage' && active && groupActive ? 'Delete Group' : null,
            icon: 'fa fa-trash',
            onClick: () => {
                deleteGroupHandler();
                dispatch(setShowChatHeaderOptionsModal(false));
            }
        }
    ];

    return (
        <div className="chat-header FRCB w100">
            {showProfile && <Profile userId={userId} setShowProfile={setShowProfile} />}
            {showGroupMembers && <GroupMembers groupId={chatId} setShowGroupMembers={setShowGroupMembers} />}
            <div className='FRCS'>
                <UserAvatar displayPicture={displayPicture} displayName={displayName} setShowProfile={setShowProfile} />
                <p className="name mL10">{displayName}</p>
                {participantsCount && active && <ParticipantsCount count={participantsCount} setShowGroupMembers={setShowGroupMembers} />}
            </div>
            <div className='FRCE'>
                <i className='header-icons fa fa-phone' id='chat-header-options' />
                <i className='header-icons fa fa-video mL10' id='chat-header-options' />
                {showChatHeaderOptionsModalSlice && <OptionsModal options={options} />}
                <i className='header-icons fa fa-ellipsis-v mL10' id='chat-header-options' onClick={chatHeaderOptionsClickHandler} />
                <i className='header-icons fa fa-close mL10' id='close-chat' onClick={closeChatHandler}></i>
            </div>
        </div>
    );
};


export default ChatHeader