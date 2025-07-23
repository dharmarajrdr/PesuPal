import React, { useEffect, useState } from 'react'
import './GroupMembers.css';
import UserPreview from '../../User/UserPreview';
import { apiRequest } from '../../../http_request';

const RoleComponent = ({ role, members }) => {

    return <div key={role} id='group-member-role'>
        <h3>{role.replace(/_/g, ' ')}</h3>
        <ul>
            {members[role]?.map(member => (
                !member.archived && <UserPreview user_detail={member} key={member.id} />
            ))}
        </ul>
    </div>
}

const NoMembersFound = () => {

    return (
        <div className='FCCC w100 h100P' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-users mR5' />
                No members found.
            </p>
        </div>
    )
}

const GroupMembers = ({ groupId, setShowGroupMembers }) => {

    const [members, setMembers] = useState({});
    const roleOrder = ["SUPER_ADMIN", "ADMIN", "USER"];

    const closeOverlayHandler = (e) => {
        if (e.target.id === 'group-members-overlay') {
            setShowGroupMembers(false);
        }
    }

    useEffect(() => {
        apiRequest(`/api/v1/group-chat-member/members/${groupId}`, 'GET').then(response => {
            setMembers(response.data);
        }).catch(error => {
            console.error("Error fetching group members:", error);
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
                        ) : <NoMembersFound />
                    }
                </div>
            </div>
        </div>
    )
}

export default GroupMembers