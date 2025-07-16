import { useState } from "react";
import './UserAvatar.css';

const UserAvatar = ({ displayPicture }) => {

    const [imageError, setImageError] = useState(false);

    return imageError || !displayPicture ? (
        <i className="fa fa-user-circle user-avatar-placeholder mR10" aria-hidden="true" />
    ) : (
        <img src={displayPicture} alt="User" className="img_40_40 mR10" onError={() => setImageError(true)} />
    );
};

export default UserAvatar;
