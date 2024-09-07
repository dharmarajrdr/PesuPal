import React from 'react'
import './PinnedUsers.css'
import { StatusIndicator } from '../../Auth/utils'

const PinnedUser = ({ name, status, image }) => {
    const customStyle = { bottom: '0px', right: '-3px', fontSize: '9px', padding: '0px' };
    return (
        <div className='PinnedUserContainer FCCC'>
            <div className='pR'>
                <img src={image} />
                <StatusIndicator status={status} style={customStyle} />
            </div>
            <span>{name}</span>
        </div>
    )
}

export default PinnedUser