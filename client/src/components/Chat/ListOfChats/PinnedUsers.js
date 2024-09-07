import './PinnedUsers.css'
import React from 'react';
import pinnedUsersList from './PinnedUsersList';
import PinnedUser from './PinnedUser';

const PinnedUsers = () => {

    return (
        <div id='PinnedUsers' className='noScollbar selectNone'>
            <div id='PinnedUsersFrame'>
                {pinnedUsersList.map(({ name, image }, index) => (
                    <PinnedUser name={name} image={image} key={index} />
                ))}
            </div>
        </div>
    );
};

export default PinnedUsers;
