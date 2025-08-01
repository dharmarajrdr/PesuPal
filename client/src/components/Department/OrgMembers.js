import { StatusIndicator } from '../Auth/utils';
import UserAvatar from '../User/UserAvatar';
import './OrgMembers.css';

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

    const { userId, displayName, email, displayPicture, status } = member;

    return (
        <div className='org-member w100 FRCB'>
            <div className='FRCS' id='left'>
                <div className='pR'>
                    <UserAvatar displayPicture={displayPicture} />
                    <StatusIndicator status={status} />
                </div>
                <div className='FCSS org-member-details'>
                    <div className='FRCB w100'>
                        <h4 className='displayName'>{displayName}</h4>
                    </div>
                    <p className='email'>{email}</p>
                </div>
            </div>
            <div className='FRCE' id='right'>
                <i className='profile_contacts fa fa-comment' style={{ backgroundColor: 'blue' }} />
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