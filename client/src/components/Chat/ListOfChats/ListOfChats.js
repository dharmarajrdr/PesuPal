import './ListOfChats.css'
import SubTabs from './SubTabs'
import { useState } from 'react'
import SearchUsers from './SearchUsers'
import PinnedUsers from './PinnedUsers'
import RecentChats from './RecentChats'

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