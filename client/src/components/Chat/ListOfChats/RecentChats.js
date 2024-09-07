import React from 'react'
import './RecentChats.css'
import PinnedUsersList from './PinnedUsersList'
import RecentChat from './RecentChat'

const RecentChats = () => {
    return (
        <div id='RecentChats' className='FCCS w100'>
            {PinnedUsersList.map(({ name, status, image, recent_message }, index) =>
                <RecentChat key={index} name={name} image={image} status={status} recent_message={recent_message} />
            )}
        </div>
    )
}

export default RecentChats