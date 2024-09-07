import React from 'react'
import ConversationScreen from './ConversationScreen/ConversationScreen'
import ListOfChats from './ListOfChats/ListOfChats'

const ChatLayout = () => {
    return (
        <div className='FRSS w100 h100'>
            <ListOfChats />
            <ConversationScreen />
        </div>
    )
}

export default ChatLayout