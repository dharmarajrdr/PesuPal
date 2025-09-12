import './ListOfChats.css'
import SubTabs from './SubTabs'
import { useRef, useState } from 'react'
import SearchUsers from './SearchUsers'
import PinnedUsers from './PinnedUsers'
import RecentChats from './RecentChats'

const ListOfChats = () => {

    const recentChatsRef = useRef(null);
    const [searchChat, setSearchChat] = useState('');

    return (
        <div id='ListOfChats' className='custom-scrollbar'>
            <div id='searchPinnedFixedContainer'>
                <SearchUsers searchChat={searchChat} setSearchChat={setSearchChat} />
                <SubTabs />
                <PinnedUsers recentChatsRef={recentChatsRef} />
            </div>
            <RecentChats searchChat={searchChat} recentChatsRef={recentChatsRef} />
        </div>
    )
}

export default ListOfChats