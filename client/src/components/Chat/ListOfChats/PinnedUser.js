import React from 'react'
import './PinnedUsers.css'

const PinnedUser = ({ name, image }) => {
    return (
        <div className='PinnedUserContainer FCCC'>
            <img src={image} />
            <span>{name}</span>
        </div>
    )
}

export default PinnedUser