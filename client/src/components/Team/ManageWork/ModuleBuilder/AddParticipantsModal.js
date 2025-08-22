import Loader from '../../../Loader';
import { useDispatch } from 'react-redux';
import { useEffect, useState } from 'react'
import { apiRequest } from '../../../../http_request';
import { showPopup } from '../../../../store/reducers/PopupSlice';
import { showProfile } from '../../../../store/reducers/ProfileSlice';
import { incrementModuleMemberCount } from '../../../../store/reducers/CurrentModuleSlice';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../../store/reducers/ConfirmationPopupSlice';


const SearchUsers = ({ searchQuery, setSearchQuery }) => {
    return (
        <div id='search-users' className='w100 FRCC'>
            <i className='fa fa-search mR5' id='search-icon'></i>
            <input type='text' placeholder='Search users...' className='w100' value={searchQuery} onChange={(e) => setSearchQuery(e.target.value)} />
        </div>
    );
}

const AddUser = ({ user, moduleId, onClose }) => {

    const dispatch = useDispatch();
    const { id, displayPicture, displayName, email } = user || {};

    const addUserHandler = () => {
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to add this user?',
            options: [
                {
                    title: 'Add',
                    color: 'green',
                    onClick: () => {
                        apiRequest(`/api/v1/module/${moduleId}/member`, 'POST', {
                            'userId': id, moduleId, 'role': 'MEMBER'
                        }).then(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            dispatch(showPopup({ message, type: 'success' }));
                            dispatch(incrementModuleMemberCount());
                            onClose();
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
            <img src={displayPicture} alt={displayName} className='img_30_30 mR10' onClick={() => { dispatch(showProfile(id)); }} />
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

const UsersList = ({ users, moduleId, onClose }) => {

    return <div className='w100' id='add-participants-users-list'>
        {users.length ?
            users.map((user, index) => <AddUser key={index} user={user} moduleId={moduleId} onClose={onClose} />) :
            <NoUserFound />}
    </div>
}

const AddParticipantsModal = ({ moduleId, onClose }) => {

    const debounceDelay = 1000; // Delay in milliseconds
    const dispatch = useDispatch();
    const [users, setUsers] = useState([]);
    const [loader, setLoader] = useState(true);
    const [searchQuery, setSearchQuery] = useState('');
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);

    const getUsers = () => {
        apiRequest(`/api/v1/module/${moduleId}/non-members?search=${searchQuery}&page=${page}&size=${size}`, 'GET').then(({ data }) => {
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
                <h3 id='add-participants-title' className='w100'>Add Members</h3>
                {loader ? <Loader /> : <>
                    <SearchUsers moduleId={moduleId} setSearchQuery={setSearchQuery} />
                    <UsersList users={users} moduleId={moduleId} onClose={onClose} />
                </>}
            </div>
        </div>
    )
}


export default AddParticipantsModal