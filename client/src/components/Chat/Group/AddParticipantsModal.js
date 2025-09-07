import Loader from '../../Loader';
import './AddParticipantsModal.css';
import { useDispatch } from "react-redux";
import { useEffect, useRef, useState } from 'react';
import { apiRequest } from '../../../http_request';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { showProfile } from '../../../store/reducers/ProfileSlice';
import { increaseParticipantsCount } from '../../../store/reducers/CurrentChatPreviewSlice';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';

const SearchUsers = ({ searchQuery, setSearchQuery }) => {
    return (
        <div id='search-users' className='w100 FRCC'>
            <i className='fa fa-search mR5' id='search-icon'></i>
            <input type='text' placeholder='Search users...' className='w100' value={searchQuery} onChange={(e) => setSearchQuery(e.target.value)} />
        </div>
    );
}

const FirstChar = ({ displayName }) => {

    return <p className='first-char-of-name'>{displayName.charAt(0).toUpperCase()}</p>
}

const AddUser = ({ user, groupId }) => {

    const dispatch = useDispatch();
    const { id, displayPicture, displayName, email } = user || {};
    const [showDisplayPicture, setShowDisplayPicture] = useState(displayPicture != null);

    const addUserHandler = () => {
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to add this user?',
            options: [
                {
                    title: 'Add',
                    color: 'green',
                    onClick: () => {
                        apiRequest(`/api/v1/group-chat-member/add-member`, 'POST', { userId: id, groupId }).then(({ message }) => {
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

    return <div key={id} className='user-preview FRCB w100'>
        <div className='FRCS'>
            {showDisplayPicture ? <img src={displayPicture} onError={() => setShowDisplayPicture(false)} alt={displayName} className='img_30_30 mR10' onClick={() => { dispatch(showProfile(id)); }} /> : <FirstChar displayName={displayName} />}
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
            users.map((user, index) => <AddUser key={index} user={user} groupId={groupId} />) :
            <NoUserFound />}
    </div>
}

const AddParticipantsModal = ({ groupId, onClose }) => {

    const debounceDelay = 1000; // Delay in milliseconds
    const dispatch = useDispatch();
    const [users, setUsers] = useState([]);
    const [loader, setLoader] = useState(true);
    const [searchQuery, setSearchQuery] = useState('');
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);

    const firstRender = useRef(true);

    const getUsers = () => {
        apiRequest(`/api/v1/group-chat-member/non-participants/${groupId}?search=${searchQuery}&page=${page}&size=${size}`, 'GET').then(({ data }) => {
            setUsers(data);
            setLoader(false);
        }).catch(({ message }) => {
            setUsers([]);
            dispatch(showPopup({ message, type: 'error' }));
        });
    }

    // Fetch users when the component mounts
    useEffect(getUsers, []);

    // Fetch users when the search query changes - Debounce with a delay
    useEffect(() => {
        if (firstRender.current) {
            firstRender.current = false;
            return;
        }
        const timer = setTimeout(getUsers, debounceDelay);
        return () => clearTimeout(timer);
    }, [searchQuery]);

    return (
        <div id='add-participants-modal' className='FCCC entire-screen-overlay' onClick={(e) => {
            e.stopPropagation();
            if (e.target.id === 'add-participants-modal') {
                onClose();
            }
        }}>
            <div id='add-participants-modal-content' className='FCCS centerMe'>
                <h3 id='add-participants-title' className='w100'>Add Participants</h3>
                {loader ? <Loader /> : <>
                    <SearchUsers groupId={groupId} setSearchQuery={setSearchQuery} />
                    <UsersList users={users} groupId={groupId} />
                </>}
            </div>
        </div>
    )
}

export default AddParticipantsModal