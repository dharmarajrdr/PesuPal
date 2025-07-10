import React from 'react'
import ConversationScreen from './ConversationScreen/ConversationScreen'
import ListOfChats from './ListOfChats/ListOfChats';
import './ChatLayout.css'
import SubscriptionPlan from '../Subscription/SubscriptionPlan';
import OrgList from '../Org/OrgList';

const ChatLayout = () => {
    return (
        <div className='Layout FRCS'>
            <ListOfChats />
            <ConversationScreen />
            {/* <OrgList /> */}
            {/* <SubscriptionPlan /> */}
        </div>
    )
}

export default ChatLayout