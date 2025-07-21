import './PinnedUsers.css'
import { StatusIndicator } from '../../Auth/utils'
import { useNavigate } from 'react-router-dom';

const PinnedUser = ({ item }) => {

    const navigate = useNavigate();

    const customStyle = { bottom: '0px', right: '-3px', fontSize: '9px', padding: '0px' };
    const { displayName, displayPicture, status, chatId } = item || {};

    const pinnedUserClickHandler = () => {
        navigate(`/chat/${chatId}`);
    }

    return (
        <div className='PinnedUserContainer FCCC' onClick={pinnedUserClickHandler}>
            <div className='pR'>
                <img src={displayPicture} className='objectFitCover' />
                <StatusIndicator status={status} style={customStyle} />
            </div>
            <span>{displayName}</span>
        </div>
    )
}

export default PinnedUser