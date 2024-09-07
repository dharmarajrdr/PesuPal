import React from 'react'
import ConversationScreen from './ConversationScreen/ConversationScreen'
import ListOfChats from './ListOfChats/ListOfChats';
import './ChatLayout.css'

const ChatLayout = () => {
    return (
        <div className='h100 ChatLayout'>
            <ListOfChats />
            <ConversationScreen />
        </div>
    )
}

export default ChatLayout