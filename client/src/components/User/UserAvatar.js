import { useState } from "react";
import './UserAvatar.css';
import utils from "../../utils";



const UserAvatar = ({ displayPicture, displayName, setShowProfile }) => {

    const [imageError, setImageError] = useState(false);

    return (imageError || !displayPicture) ? (
        displayName ? (
            <p className="user-avatar-placeholder-first-character img_40_40" style={{ backgroundColor: utils.uniqueColorGenerator(displayName) }}>
                {displayName.charAt(0).toUpperCase()}
            </p>
        ) : (
            <i className="fa fa-user-circle user-avatar-placeholder" aria-hidden="true" />
        )
    ) : (
        <img src={displayPicture} alt="User" className="img_40_40 cursP objectFitCover" onError={() => setImageError(true)} onClick={() => setShowProfile ? setShowProfile(true) : null} />
    );
};

export default UserAvatar;
