import React from 'react'
import './PinnedUsers.css'
import { StatusIndicator } from '../../Auth/utils'

const PinnedUser = ({ item }) => {

    const customStyle = { bottom: '0px', right: '-3px', fontSize: '9px', padding: '0px' };
    const { displayName, displayPicture, status, chatId } = item || {};

    return (
        <div className='PinnedUserContainer FCCC'>
            <div className='pR'>
                <img src={displayPicture} className='objectFitCover' />
                <StatusIndicator status={status} style={customStyle} />
            </div>
            <span>{displayName}</span>
        </div>
    )
}

export default PinnedUser