import './SubTabs.css';
import { Link } from 'react-router-dom'

const SubTabs = () => {
    return (
        <div className='FRCC' id='subtab_container'>
            <Link to='/chat/messages' className='subtabs'>
                <span>Messages</span>
                <b className='notifyCount'>17</b>
            </Link>
            <Link to='/chat/groups' className='subtabs'>
                <span>Groups</span>
                <b className='notifyCount'>12</b>
            </Link>
            <Link to='/chat/channels' className='subtabs'>
                <span>Channels</span>
                <b className='notifyCount'>15</b>
            </Link>
        </div>
    )
}

export default SubTabs