import './PinnedUsers.css'
import React from 'react';
import pinnedUsersList from './PinnedUsersList';
import PinnedUser from './PinnedUser';

const PinnedUsers = () => {

    return (
        <div id='PinnedUsers' className='noScrollbar selectNone'>
            <div id='PinnedUsersFrame'>
                {pinnedUsersList.map(({ name, status, image }, index) => (
                    <PinnedUser name={name} status={status} image={image} key={index} />
                ))}
            </div>
        </div>
    );
};

export default PinnedUsers;
