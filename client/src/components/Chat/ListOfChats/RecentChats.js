import React from 'react'
import './RecentChats.css'
import PinnedUsersList from './PinnedUsersList'
import RecentChat from './RecentChat'

const RecentChats = () => {
    return (
        <div id='RecentChats' className='FCCS w100'>
            {PinnedUsersList.map(({ name, status, image, recentMessage }, index) =>
                <RecentChat key={index} name={name} image={image} status={status} recentMessage={recentMessage} />
            )}
        </div>
    )
}

export default RecentChats