import { useState } from 'react';
import './Inviter.css'
import utils from '../../utils';

const Inviter = ({ inviter, invitedAt }) => {

    const { displayName, email, displayPicture } = inviter;
    const [showDisplayPicture, setShowDisplayPicture] = useState(displayPicture != null);

    return (
        <div className='FCSC inviter'>
            <div className='FRCS'>
                {showDisplayPicture ? <img className='img_20_20' src={displayPicture} onError={() => setShowDisplayPicture(false)} /> : <span className='img_20_20 first-char'>{displayName.trim().charAt(0).toUpperCase()}</span>}
                <span className='pL5 fs14'>{displayName}</span>
            </div>
            <span className='fs10 color777' style={{ marginTop: '4px' }}>Invited on {utils.convertDateAndTime(invitedAt)}</span>
        </div>
    )
}

export default Inviter