import './StartNewChat.css';
import Loader from '../Loader';
import { useDispatch } from "react-redux";
import { useNavigate } from 'react-router-dom';
import { apiRequest } from '../../http_request';
import { useEffect, useRef, useState } from 'react';
import { showPopup } from '../../store/reducers/PopupSlice';
import { showProfile } from '../../store/reducers/ProfileSlice';

const SearchUsers = ({ searchQuery, setSearchQuery }) => {
    return (
        <div id='search-users' className='w100 FRCC'>
            <i className='fa fa-search mR5' id='search-icon'></i>
            <input type='text' placeholder='Search users...' className='w100' value={searchQuery} onChange={(e) => setSearchQuery(e.target.value)} />
        </div>
    );
}

const FirstChar = ({ displayName }) => {

    return <p className='first-char-of-name' style={{ width: '30px', height: '30px', lineHeight: '29px', fontSize: '14px' }}>{displayName.charAt(0).toUpperCase()}</p>
}

const Row = ({ user, onClose }) => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const { id, chatId, displayPicture, displayName, email } = user || {};
    const [showDisplayPicture, setShowDisplayPicture] = useState(displayPicture != null);
    const route = `/chat/messages/${chatId}`;

    const chatButtonClicked = () => {
        navigate(route);
        onClose();
    }

    return chatId && <div key={chatId} className='user-preview FRCB w100'>
        <div className='FRCS'>
            {showDisplayPicture ? <img src={displayPicture} onError={() => setShowDisplayPicture(false)} alt={displayName} className='img_30_30 mR10' onClick={() => { dispatch(showProfile(id)); }} /> : <FirstChar displayName={displayName} />}
            <div className='FCSS'>
                <h6>{displayName}</h6>
                <span className='fs10 mT2 color999'>{email}</span>
            </div>
        </div>
        <span className='fs12 color777 add-user-button' onClick={chatButtonClicked}>Chat</span>
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

const UsersList = ({ users, onClose }) => {

    return <div className='w100' id='add-participants-users-list'>
        {users.length ?
            users.map((user, index) => <Row key={index} user={user} onClose={onClose} />) :
            <NoUserFound />}
    </div>
}

const StartNewChat = ({ onClose }) => {

    const debounceDelay = 1000; // Delay in milliseconds
    const dispatch = useDispatch();
    const [users, setUsers] = useState([]);
    const [loader, setLoader] = useState(true);
    const [searchQuery, setSearchQuery] = useState('');
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);

    const firstRender = useRef(true);

    const getUsers = () => {
        apiRequest(`/api/v1/people/search?query=${searchQuery.trim()}&page=${page}&size=${size}`, "GET").then(({ data }) => {
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
                <h3 id='add-participants-title' className='w100'>Start New Chat</h3>
                {loader ? <Loader /> : <>
                    <SearchUsers setSearchQuery={setSearchQuery} />
                    <UsersList users={users} onClose={onClose} />
                </>}
            </div>
        </div>
    )
}

export default StartNewChat