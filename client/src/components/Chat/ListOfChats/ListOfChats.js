import './ListOfChats.css'
import SearchUsers from './SearchUsers'
import PinnedUsers from './PinnedUsers'
import RecentChats from './RecentChats'
import SubTabs from './SubTabs'

const ListOfChats = () => {

    return (
        <div id='ListOfChats' className='custom-scrollbar'>
            <div id='searchPinnedFixedContainer'>
                <SearchUsers />
                <PinnedUsers />
                <SubTabs />
            </div>
            <RecentChats />
        </div>
    )
}

export default ListOfChats