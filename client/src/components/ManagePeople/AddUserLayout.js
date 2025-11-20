import './AddUserLayout.css';
import { useState } from 'react';
import { apiRequest } from '../../http_request';
import { useDispatch, useSelector } from "react-redux";
import { showPopup } from '../../store/reducers/PopupSlice';
import { prependUser } from '../../store/reducers/ManageUsersSlice';
import { hideLoader, showLoader } from '../../store/reducers/VerticalLoaderSlice';
import { hideConfirmationPopup, showConfirmationPopup } from '../../store/reducers/ConfirmationPopupSlice';

const AddUserLayout = ({ setShowAddUserLayout }) => {

    const dispatch = useDispatch();
    const [email, setEmail] = useState('');
    const [displayName, setDisplayName] = useState('');
    const { title } = useSelector(state => state.manageUsers);

    const closeLayout = (e) => {
        e.stopPropagation();
        setShowAddUserLayout(false);
    };

    const _finally = () => {
        dispatch(hideConfirmationPopup());
        dispatch(hideLoader());
        setShowAddUserLayout(false);
    }

    console.log(title);

    const sendInviteHandler = () => {
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to invite this user?',
            options: [
                {
                    "title": "Invite",
                    "color": "#00a434ff",
                    "onClick": () => {
                        dispatch(showLoader());
                        apiRequest(`/api/v1/org-invitations`, 'POST', { email, displayName }).then(({ data, message }) => {
                            _finally();
                            if (title == 'Pending Invites') {
                                dispatch(prependUser(data));
                            }
                            dispatch(showPopup({ message, type: 'success' }));
                        }).catch(({ message }) => {
                            _finally();
                            dispatch(showPopup({ message, type: 'error' }));
                        });
                    }
                },
                {
                    "title": "Cancel",
                    "color": "gray",
                    "onClick": () => {
                        _finally();
                    }
                }
            ]
        }));
    }

    return (
        <div className='entire-screen-overlay' id='add-user-layout-overlay'>
            <div id='add-user-layout' className='centerMe FCSS'>
                <h3 className='w100' id='add-user-title'>Add User</h3>
                <div className='FRCC w100 row'>
                    <i className='fa fa-envelope label-icons' />
                    <input type="email" placeholder="Enter user email" value={email} onChange={(e) => setEmail(e.target.value)} />
                </div>
                <div className='FRCC w100 row'>
                    <i className='fa fa-user label-icons' />
                    <input type='text' placeholder='Enter name' value={displayName} onChange={(e) => setDisplayName(e.target.value)} />
                </div>
                <div className='FRCE w100 row'>
                    <button id='cancel-btn' onClick={closeLayout}>Cancel</button>
                    <button id='send-invite-btn' className='mL10' onClick={sendInviteHandler}>
                        <i className='fa fa-envelope w15'></i> Send Invite
                    </button>
                </div>
            </div>
        </div>
    )
}

export default AddUserLayout