import React from 'react'
import ConversationScreen from './ConversationScreen/ConversationScreen'
import ListOfChats from './ListOfChats/ListOfChats';
import './ChatLayout.css'

const ChatLayout = () => {
    return (
        <div className='Layout'>
            <ListOfChats />
            <ConversationScreen />
        </div>
    )
}

export default ChatLayout