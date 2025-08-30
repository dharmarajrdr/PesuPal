import './PinnedUsers.css'
import { StatusIndicator } from '../../Auth/utils'
import { useNavigate } from 'react-router-dom';
import UserAvatar from '../../User/UserAvatar';
import { useSelector } from 'react-redux';

const PinnedUser = ({ item }) => {

    const navigate = useNavigate();

    const customStyle = { bottom: '0px', right: '-3px', fontSize: '9px', padding: '0px' };
    const { displayName, displayPicture, status, chatId } = item || {};
    const { chatMode } = useSelector(state => state.activeChatTab);

    const pinnedUserClickHandler = () => {
        if (chatMode == 'DIRECT_MESSAGE') {
            navigate(`/chat/messages/${chatId}`);
        } else if (chatMode == 'GROUP_MESSAGE') {
            navigate(`/chat/groups/${chatId}`);
        }
    }

    return (
        <div className='PinnedUserContainer FCCC' onClick={pinnedUserClickHandler}>
            <div className='pR'>
                <UserAvatar displayName={displayName} displayPicture={displayPicture} />
                {chatMode === 'DIRECT_MESSAGE' && <StatusIndicator status={status} style={customStyle} />}
            </div>
            <span>{displayName}</span>
        </div>
    )
}

export default PinnedUser