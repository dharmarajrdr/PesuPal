import React, { useEffect, useState } from 'react'
import './GroupMembers.css';
import UserPreview from '../../User/UserPreview';
import { apiRequest } from '../../../http_request';
import { useDispatch } from 'react-redux'

const RoleComponent = ({ role, members }) => {

    const items = members[role] || [];
    const formattedRole = role.replace(/_/g, ' ').toLowerCase().replace(/\b\w/g, char => char.toUpperCase());

    return items.length > 0 && <div key={role} id='group-member-role'>
        <h3>{formattedRole}</h3>
        <ul>
            {items.map(member => (
                !member.archived && <UserPreview user_detail={member} key={member.id} />
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

    const dispatch = useDispatch();
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
                            roleOrder.map(role => <RoleComponent role={role} members={members} key={role} />)
                        ) : <NoMembersFound message={error} />
                    }
                </div>
            </div>
        </div>
    )
}

export default GroupMembers