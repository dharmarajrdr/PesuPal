import React from 'react'
import './ListOfChats.css'
import SearchUsers from './SearchUsers'
import PinnedUsers from './PinnedUsers'
import RecentChats from './RecentChats'

const ListOfChats = () => {
    return (
        <div id='ListOfChats'>
            <div id='searchPinnedFixedContainer'>
                <SearchUsers />
                <PinnedUsers />
            </div>
            <RecentChats />
        </div>
    )
}

export default ListOfChats