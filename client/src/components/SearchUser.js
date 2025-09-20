import './SearchUser.css';
import { apiRequest } from '../http_request';
import { useEffect, useRef, useState } from 'react';
import { showPopup } from '../store/reducers/PopupSlice';
import { useDispatch } from 'react-redux';

const FirstCharOfName = ({ string }) => {

    return <p className='first-char-of-name'>{string.charAt(0).toUpperCase()}</p>
}

const Row = ({ user, selectedUsers, userClickedHandler }) => {

    const { id, displayName, displayPicture } = user || {};
    const [showDisplayPicture, setShowDisplayPicture] = useState(displayPicture != null);
    const isUserSelected = selectedUsers.find(selectedUser => selectedUser.id === id);
    return (
        <div key={id} className={`user-suggestion w100 FRCS ${isUserSelected ? 'selected' : ''}`} onClick={(e) => userClickedHandler(e, user)}>
            {showDisplayPicture ? <img src={displayPicture} alt={displayName} onError={() => setShowDisplayPicture(false)} /> : <FirstCharOfName string={displayName} />}
            <span>{displayName}</span>
        </div>
    )
}

const SelectedUser = ({ user, unSelectUserHandler }) => {

    const { id, displayName, displayPicture } = user || {};
    const [showDisplayPicture, setShowDisplayPicture] = useState(displayPicture != null);
    return (
        <div key={id} className="selected-user FCCC" title={displayName} onClick={(e) => unSelectUserHandler(e, user)}>
            <span className='remove-user-btn'>
                <i className='fa fa-xmark'></i>
            </span>
            {showDisplayPicture ? <img src={displayPicture} alt={displayName} onError={() => setShowDisplayPicture(false)} /> : <FirstCharOfName string={displayName} />}
        </div>
    )
}

const SearchUser = ({ maxUsersSelectable, selectedUsers, setSelectedUsers }) => {

    const dispatch = useDispatch();
    const searchRef = useRef(null);
    const firstRender = useRef(true);
    const searchInputRef = useRef(null);
    const [users, setUsers] = useState([]);
    const [searchName, setSearchName] = useState('');
    const [showSuggestions, setShowSuggestions] = useState(false);

    const getUsers = () => {
        apiRequest(`/api/v1/people/search?query=${searchName.trim()}`, "GET").then(({ data }) => {
            setUsers(data);
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    }

    useEffect(getUsers, []);

    useEffect(() => {

        if (firstRender.current) {
            firstRender.current = false;
            return;
        }

        const timer = setTimeout(() => {
            getUsers();
        }, 500);
        return () => clearTimeout(timer);

    }, [searchName]);

    const userClickedHandler = (e, user) => {
        e.stopPropagation();
        const isUserSelected = selectedUsers.find(selectedUser => selectedUser.id === user.id);
        if (!isUserSelected) {
            if (maxUsersSelectable && selectedUsers.length >= maxUsersSelectable) {
                dispatch(showPopup({ message: `You can select maximum ${maxUsersSelectable} users`, type: 'error' }));
                return;
            }
            setSelectedUsers(prevSelectedUsers => [...prevSelectedUsers, user]);
        } else {
            setSelectedUsers(prevSelectedUsers => prevSelectedUsers.filter(selectedUser => selectedUser.id !== user.id));
        }
    }

    const unSelectUserHandler = (e, user) => {
        e.stopPropagation();
        setSelectedUsers(prevSelectedUsers => prevSelectedUsers.filter(selectedUser => selectedUser.id !== user.id));
    }

    const onInputFocusHandler = () => {
        setShowSuggestions(true);
    }

    // Close suggestions on outside click
    useEffect(() => {
        function handleClickOutside(event) {
            if (searchRef.current && !searchRef.current.contains(event.target)) {
                setShowSuggestions(false);
            }
        }
        document.addEventListener("mousedown", handleClickOutside);
        return () => document.removeEventListener("mousedown", handleClickOutside);
    }, []);

    return (
        <div id="search-users-container" ref={searchRef}>
            {showSuggestions && <div id='user-suggestions-container' className='FCCE'>
                <div id='suggestions' className='FCCS'>
                    <div id='user-suggestions-slider'>
                        {users.length ? users.map(user => <Row user={user} selectedUsers={selectedUsers} userClickedHandler={userClickedHandler} />) : <p id='no-users-found' className='FRCC'>No users found</p>}
                    </div>
                </div>
                {selectedUsers.length > 0 && <div id='selected-users' className='FRCS w100'>
                    <div id='selected-user-slider' className='FRCS noScrollbar'>
                        {selectedUsers.map(user => {
                            <SelectedUser user={user} unSelectUserHandler={unSelectUserHandler} />
                        })}
                    </div>
                </div>}
            </div>}
            <div id='search-user-input-container' className={`FRCC ${showSuggestions ? 'showing-suggestions' : ''}`}>
                <input type="text" placeholder="Search users..." ref={searchInputRef} id='search-user-input' autoComplete='off' spellCheck='false' value={searchName} onFocus={onInputFocusHandler} onChange={(e) => setSearchName(e.target.value)} />
            </div>
        </div>
    )
}

export default SearchUser