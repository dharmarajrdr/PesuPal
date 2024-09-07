import React from 'react'
import ConversationScreen from './ConversationScreen/ConversationScreen'
import ListOfChats from './ListOfChats/ListOfChats'

const ChatLayout = () => {
    const chatLayoutStyle = {
        width: 'calc(100% - 75px)',
        border: '1px solid red',
        overflow: 'hidden'
    }
    return (
        <div className='h100' style={chatLayoutStyle}>
            <ListOfChats />
            <ConversationScreen />
        </div>
    )
}

export default ChatLayout