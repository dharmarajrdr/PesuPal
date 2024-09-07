import React from 'react'
import LeftNavigation from '../LeftNavigation/LeftNavigation'
import ChatLayout from '../Chat/ChatLayout'

const Main = () => {
    return (
        <div className='FRSS' style={{height:'100%'}}>
            <LeftNavigation />
            <ChatLayout />
        </div>
    )
}

export default Main