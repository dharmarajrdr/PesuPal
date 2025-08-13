import { useState } from 'react';
import './AddParticipantsModal.css';
import { useDispatch } from "react-redux";
import Profile from '../../OthersProfile/Profile';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';
import { apiRequest } from '../../../http_request';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { increaseParticipantsCount, updateCurrentChatPreview } from '../../../store/reducers/CurrentChatPreviewSlice';

const users = [
    {
        "userId": "CSaW4OXI2p31",
        "displayName": "Avinash",
        "displayPicture": "https://res.cloudinary.com/jerrick/image/upload/d_642250b563292b35f27461a7.png,f_jpg,fl_progressive,q_auto,w_1024/6472190e60dc86001dd79d74.jpg",
        "designation": "CEO",
        "department": "Executive Department",
        "status": "Away",
        "email": "avinash.s@gmail.com",
        "phone": "8373643832",
        "chatId": "Pd8t0LiAPVCd"
    },
    {
        "userId": "AMwYOJWFAkoQ",
        "displayName": "Dharmaraj R",
        "displayPicture": "https://www.updatenews360.com/english/wp-content/uploads/2022/04/Xefntr7z_400x400.jpg",
        "designation": "CEO",
        "department": "Executive Department",
        "status": "Away",
        "email": "dharmaraj.171215@gmail.com",
        "phone": "1234567890"
    },
    {
        "userId": "LBnISThbWwHf",
        "displayName": "GK",
        "displayPicture": "https://media.themoviedb.org/t/p/w235_and_h235_face/6naZ3oybdCtfggc5pTrcBDxOXrP.jpg",
        "designation": "CEO",
        "department": "Executive Department",
        "status": "Away",
        "email": "gopikrishna@gmail.com",
        "phone": "84635374843",
        "chatId": "Es7pTYsebhBe"
    },
    {
        "userId": "uexJUG4ral4j",
        "displayName": "Mohankumar R",
        "displayPicture": "https://pbs.twimg.com/media/E03qKiHUYAE9tNW.jpg:large",
        "designation": "CEO",
        "department": "Executive Department",
        "status": "Away",
        "email": "mohan.rmk@gmail.com",
        "phone": "8072113856",
        "chatId": "MHI3f5RkAQg6"
    },
    {
        "userId": "DK1Z0xGAulnv",
        "displayName": "Sachin Krishna Moger",
        "displayPicture": "https://hyderabadmail.com/wp-content/uploads/2024/06/actor-Shiva-Rajkumar.png",
        "designation": "CEO",
        "department": "Executive Department",
        "status": "Away",
        "email": "sachin.moger@gmail.com",
        "phone": "9473638493",
        "chatId": "1MwGX5UMVS5V"
    },
    {
        "userId": "ImW6RVsyA9iv",
        "displayName": "Sudharshan S",
        "displayPicture": "https://i.pinimg.com/736x/d6/1b/4e/d61b4ed11bff9c1002425b04951deb24.jpg",
        "designation": "CEO",
        "department": "Executive Department",
        "status": "Away",
        "email": "sudharshan.sa@gmail.com",
        "phone": "94736282222",
        "chatId": "jLUsOaRbQx8y"
    },
    {
        "userId": "87CO5CpcMTGh",
        "displayName": "Thamizhselvan",
        "displayPicture": "https://static.toiimg.com/thumb/msid-121444693,width-400,resizemode-4/Virat-Kohli.jpg",
        "designation": "CEO",
        "department": "Executive Department",
        "status": "Away",
        "email": "thamizhselvan@gmail.com",
        "phone": "2727272727",
        "chatId": "lINOW6VMyNPa"
    },
    {
        "userId": "Hn4Z97sZtPlS",
        "displayName": "Vinu",
        "displayPicture": "https://images.news18.com/ibnlive/uploads/2021/12/ajith-kumar-3.jpg",
        "designation": "CEO",
        "department": "Executive Department",
        "status": "Away",
        "email": "vinuvarshith@gmail.com",
        "phone": "2736355222",
        "chatId": "pZa29elvjf50"
    }
]

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
        <div id='add-participants-modal' className='FCCC entire-screen-overlay' onClick={onClose}>
            <div id='add-participants-modal-content' className='FCCS centerMe'>
                <h3 id='add-participants-title' className='w100'>Add Participants</h3>
                <SearchUsers groupId={groupId} />
                <UsersList users={users} groupId={groupId} />
            </div>
        </div>
    )
}

export default AddParticipantsModal