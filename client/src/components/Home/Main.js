import React from 'react'
import LeftNavigation from '../LeftNavigation/LeftNavigation'
import ChatLayout from '../Chat/ChatLayout'
import Profile from '../OthersProfile/Profile'
import SomeProfile from '../OthersProfile/SomeProfile'

const Main = () => {
    return (
        <div className='FRSS' style={{ height: '100%' }}>
            <LeftNavigation />
            <ChatLayout />
            <Profile Profile={SomeProfile} />
        </div>
    )
}

export default Main