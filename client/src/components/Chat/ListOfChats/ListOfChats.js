import './ListOfChats.css'
import SearchUsers from './SearchUsers'
import PinnedUsers from './PinnedUsers'
import RecentChats from './RecentChats'
import SubTabs from './SubTabs'
import { useState } from 'react'

const ListOfChats = () => {

    const [searchChat, setSearchChat] = useState('');

    return (
        <div id='ListOfChats' className='custom-scrollbar'>
            <div id='searchPinnedFixedContainer'>
                <SearchUsers searchChat={searchChat} setSearchChat={setSearchChat} />
                <SubTabs />
                <PinnedUsers />
            </div>
            <RecentChats searchChat={searchChat} />
        </div>
    )
}

export default ListOfChats