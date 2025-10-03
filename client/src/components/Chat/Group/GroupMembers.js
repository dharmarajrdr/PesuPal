import './GroupMembers.css';
import { useEffect, useState } from 'react'
import { apiRequest } from '../../../http_request';
import { useDispatch, useSelector } from "react-redux";
import { showPopup } from '../../../store/reducers/PopupSlice';
import { showProfile } from '../../../store/reducers/ProfileSlice';
import { decreaseParticipantsCount } from '../../../store/reducers/CurrentChatPreviewSlice';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';

const FirstChar = ({ displayName }) => {
    return <p className='first-char-of-name' style={{ width: '30px', height: '30px', lineHeight: '30px' }}>{displayName.charAt(0).toUpperCase()}</p>
}

const UserPreview = ({ user_detail, groupId }) => {

    const dispatch = useDispatch();
    const { id, displayName, displayPicture } = user_detail || {};
    const [showDisplayPicture, setShowDisplayPicture] = useState(displayPicture != null);
    const { groupChatConfiguration } = useSelector(state => state.currentChatPreviewSlice);
    const { removeMember: memberRemovable } = groupChatConfiguration || {};

    const removeMemberHandler = () => {
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to remove this member?',
            options: [
                {
                    title: 'Remove',
                    color: 'red',
                    onClick: () => {
                        apiRequest(`/api/v1/group-chat-member/remove-member`, 'DELETE', {
                            'userId': id, groupId
                        }).then(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            dispatch(decreaseParticipantsCount());
                            dispatch(showPopup({ message, type: 'success' }));
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
    }

    return <div key={id} className='user-preview FRCB w100'>
        <div className='FRCS'>
            {showDisplayPicture ? <img src={displayPicture} alt={displayName} className='img_30_30 mR10' onClick={() => { dispatch(showProfile(id)); }} /> : <FirstChar displayName={displayName} />}
            <h6>{displayName}</h6>
        </div>
        {memberRemovable && <span className='fs12 color777 remove-user-button' onClick={removeMemberHandler}>Remove</span>}
    </div>
}

const RoleComponent = ({ role, members, groupId }) => {

    const items = members[role] || [];
    const formattedRole = role.replace(/_/g, ' ').toLowerCase().replace(/\b\w/g, char => char.toUpperCase());

    return items.length > 0 && <div key={role} id='group-member-role'>
        <h3>{formattedRole}</h3>
        <ul>
            {items.map(member => (
                !member.archived && <UserPreview user_detail={member} key={member.id} groupId={groupId} />
            ))}
        </ul>
    </div>
}

const NoMembersFound = ({ message = "No members found." }) => {

    return (
        <div className='FCCC w100 h100P' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-users mR5' />
                {message}
            </p>
        </div>
    )
}

const GroupMembers = ({ groupId, setShowGroupMembers }) => {

    const [members, setMembers] = useState({});
    const [error, setError] = useState(null);
    const roleOrder = ["SUPER_ADMIN", "ADMIN", "USER"];

    const closeOverlayHandler = (e) => {
        if (e.target.id === 'group-members-overlay') {
            setShowGroupMembers(false);
        }
    }

    useEffect(() => {
        apiRequest(`/api/v1/group-chat-member/members/${groupId}`, 'GET').then(response => {
            setMembers(response.data);
        }).catch(({ message }) => {
            setError(message);
        });
    }, [groupId]);

    return (
        <div id='group-members-overlay' className='entire-screen-overlay' onClick={closeOverlayHandler}>
            <div id='group-members-list' className='centerMe'>
                <div id='group-members-header' className='FRCC'>
                    <h2>Participants</h2>
                </div>
                <div id='group-members-content'>
                    {
                        Object.keys(members).length > 0 ? (
                            roleOrder.map(role => <RoleComponent role={role} members={members} key={role} groupId={groupId} />)
                        ) : <NoMembersFound message={error} />
                    }
                </div>
            </div>
        </div>
    )
}

export default GroupMembers