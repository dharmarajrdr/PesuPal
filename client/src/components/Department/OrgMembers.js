import { showPopup } from '../../store/reducers/PopupSlice';
import { StatusIndicator } from '../Auth/utils';
import UserAvatar from '../User/UserAvatar';
import './OrgMembers.css';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';

const NoMembersAvailable = ({ message }) => {
    return (
        <div className='FCCC w100 h100P' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-users mR5' />
                {message}
            </p>
        </div>
    )
}

const OrgMember = ({ member }) => {

    const { userId, displayName, email, displayPicture, status, chatId } = member;
    const chatRoute = `/chat/messages/${chatId}`;
    const dispatch = useDispatch();

    const navigate = useNavigate();

    const chatClickHandler = () => {
        if (chatId === null || chatId === undefined) {
            return dispatch(showPopup({ 'message': 'Chat is not available for this user', 'type': 'error' }));
        }
        navigate(chatRoute);
    }

    return (
        <div className='org-member w100 FRCB'>
            <div className='FRCS' id='left'>
                <div className='pR mR10'>
                    <UserAvatar displayPicture={displayPicture} />
                    <StatusIndicator status={status} style={{ bottom: '-2px', right: '-5px', height: 'fit-content' }} />
                </div>
                <div className='FCSS org-member-details'>
                    <div className='FRCB w100'>
                        <h4 className='displayName'>{displayName}</h4>
                    </div>
                    <p className='email'>{email}</p>
                </div>
            </div>
            <div className='FRCE' id='right'>
                <i className='profile_contacts fa fa-comment' style={{ backgroundColor: 'blue' }} onClick={chatClickHandler} />
                <i className='profile_contacts fa fa-phone' style={{ backgroundColor: 'green' }} />
                <i className='profile_contacts fa fa-video' style={{ backgroundColor: 'red' }} />
            </div>
        </div>
    )
}

const OrgMembers = ({ title, orgMembersList, noMembersAvailableMessage }) => {
    return (
        <div id='OrgMembers' className='w100 h100'>
            <h5 className={`status-title w100 ${title.toLowerCase().replace(/\s+/, '_')}`}>
                <i className={`fa fa-circle mR5`} />
                <span className='status-span'>{title}</span>
                <span className='status-count mL5'>({orgMembersList.length})</span>
            </h5>
            {orgMembersList.length ? (
                <div className='FCSS w100'>
                    {orgMembersList.map((member, index) => <OrgMember key={index} member={member} />)}
                </div>
            ) : <NoMembersAvailable message={noMembersAvailableMessage} />}
        </div>
    )
}

export default OrgMembers