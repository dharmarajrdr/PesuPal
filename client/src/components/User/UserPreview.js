import { useState } from "react";
import utils from "../../utils";
import './UserPreview.css';
import Profile from "../OthersProfile/Profile";

const UserPreview = ({ user_detail }) => {

    const [showProfile, setShowProfile] = useState(false);
    const { userId, displayName, displayPicture, createdAt } = user_detail || {};

    return <div key={userId} className='user-preview FRCB w100'>
        {showProfile && <Profile userId={userId} setShowProfile={setShowProfile} />}
        <div className='FRCS'>
            <img src={displayPicture} alt={displayName} className='img_30_30 mR10' onClick={() => setShowProfile(true)} />
            <h6>{displayName}</h6>
        </div>
        {createdAt && <span className='fs10 color777'>{utils.convertDateAndTime(createdAt)}</span>}
    </div>
}

export default UserPreview