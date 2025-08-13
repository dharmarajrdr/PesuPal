import { useState } from 'react';
import './AddParticipantsModal.css';
import { useDispatch } from "react-redux";
import Profile from '../../OthersProfile/Profile';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';
import { apiRequest } from '../../../http_request';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { increaseParticipantsCount, updateCurrentChatPreview } from '../../../store/reducers/CurrentChatPreviewSlice';

const SearchUsers = () => {
    return (
        <div id='search-users' className='w100 FRCC'>
            <i className='fa fa-search mR5' id='search-icon'></i>
            <input type='text' placeholder='Search users...' className='w100' />
        </div>
    );
}

const AddUser = ({ user, groupId }) => {

    const dispatch = useDispatch();
    const { userId, displayPicture, displayName, email } = user || {};
    const [showProfile, setShowProfile] = useState(false);

    const addUserHandler = () => {
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to add this user?',
            options: [
                {
                    title: 'Add',
                    color: 'green',
                    onClick: () => {
                        apiRequest(`/api/v1/group-chat-member/add-member`, 'POST', { userId, groupId }).then(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            dispatch(increaseParticipantsCount());
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

    return <div key={userId} className='user-preview FRCB w100'>
        {showProfile && <Profile userId={userId} setShowProfile={setShowProfile} />}
        <div className='FRCS'>
            <img src={displayPicture} alt={displayName} className='img_30_30 mR10' onClick={() => setShowProfile(true)} />
            <div className='FCSS'>
                <h6>{displayName}</h6>
                <span className='fs10 mT2 color999'>{email}</span>
            </div>
        </div>
        <span className='fs12 color777 add-user-button' onClick={addUserHandler}>Add</span>
    </div>
}

const NoUserFound = () => {

    return (
        <div className='FCCC w100 h100P' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-user mR5' />
                No users found.
            </p>
        </div>
    )
}

const UsersList = ({ users, groupId }) => {

    return <div className='w100' id='add-participants-users-list'>
        {users.length ?
            users.map((user) => <AddUser user={user} groupId={groupId} />) :
            <NoUserFound />}
    </div>
}

const AddParticipantsModal = ({ groupId, onClose }) => {
    return (
        <div id='add-participants-modal' className='FCCC entire-screen-overlay' onClick={(e) => {
            e.stopPropagation();
            if (e.target.id === 'add-participants-modal') {
                onClose();
            }
        }}>
            <div id='add-participants-modal-content' className='FCCS centerMe'>
                <h3 id='add-participants-title' className='w100'>Add Participants</h3>
                <SearchUsers groupId={groupId} />
                <UsersList users={[]} groupId={groupId} />
            </div>
        </div>
    )
}

export default AddParticipantsModal