import './SubTabs.css';
import { Link } from 'react-router-dom'
import { useDispatch } from 'react-redux';
import { setActiveChatTab } from '../../../store/reducers/ActiveChatTabSlice';
import { setActiveRecentChat } from '../../../store/reducers/ActiveRecentChatSlice';

const SubTabs = () => {

    const dispatch = useDispatch();
    const tabsClickHandler = (tabName) => {
        dispatch(setActiveChatTab(tabName));
        dispatch(setActiveRecentChat(null));
    }

    return (
        <div className='FRCC' id='subtab_container'>
            <Link to='/chat/messages' className='subtabs' onClick={() => tabsClickHandler('directMessage')}>
                <span>Messages</span>
                <b className='notifyCount'>17</b>
            </Link>
            <Link to='/chat/groups' className='subtabs' onClick={() => tabsClickHandler('groupMessage')}>
                <span>Groups</span>
                <b className='notifyCount'>12</b>
            </Link>
            <Link to='/chat/channels' className='subtabs' onClick={() => tabsClickHandler('channelMessage')}>
                <span>Channels</span>
                <b className='notifyCount'>15</b>
            </Link>
        </div>
    )
}

export default SubTabs