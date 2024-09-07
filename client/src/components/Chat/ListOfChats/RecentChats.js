import React from 'react'
import './RecentChats.css'
import PinnedUsersList from './PinnedUsersList'
import RecentChat from './RecentChat'

const RecentChats = () => {
    return (
        <div id='RecentChats' className='FCCS w100'>
            {PinnedUsersList.map(({ name, image, recent_message }, index) =>
                <RecentChat key={index} name={name} image={image} recent_message={recent_message} />
            )}
        </div>
    )
}

export default RecentChats