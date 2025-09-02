import { useState } from 'react';
import './Inviter.css'
import utils from '../../utils';

const Inviter = ({ inviter }) => {

    const [showDisplayPicture, setShowDisplayPicture] = useState(inviter.displayPicture != null);

    return (
        <div className='FCSC inviter'>
            <div className='FRCS'>
                {showDisplayPicture ? <img className='img_20_20' src={inviter.displayPicture} onError={() => setShowDisplayPicture(false)} /> : <span className='img_20_20 first-char'>{inviter.displayName.trim().charAt(0).toUpperCase()}</span>}
                <span className='pL5 fs14'>{inviter.displayName}</span>
            </div>
            <span className='fs10 color777' style={{ marginTop: '4px' }}>Invited on {utils.convertDateAndTime(inviter.invitedAt)}</span>
        </div>
    )
}

export default Inviter