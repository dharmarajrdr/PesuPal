import { useState } from "react";
import './UserAvatar.css';

const getAvatarColor = (name) => {
    const colors = [
        '#FF5733', '#330F57', '#30FE15', '#FF33A6', '#318E15',
        '#A7AF45', '#8E44AD', '#3498DB', '#E67E22', '#2ECC71',
        '#E74C3C', '#1ABC9C', '#9B59B6', '#34495E', '#7F8C8D',
        '#F1C40F', '#D35400', '#C0392B', '#16A085', '#2980B9'
    ];

    let hash = 0;
    for (let i = 0; i < name.length; i++) {
        hash = name.charCodeAt(i) + ((hash << 5) - hash);
    }

    const index = Math.abs(hash) % colors.length;
    return colors[index];
}

const UserAvatar = ({ displayPicture, displayName, setShowProfile }) => {

    const [imageError, setImageError] = useState(false);

    return (imageError || !displayPicture) ? (
        displayName ? (
            <div className="user-avatar-placeholder-first-character" style={{ backgroundColor: getAvatarColor(displayName) }}>
                {displayName.charAt(0).toUpperCase()}
            </div>
        ) : (
            <i className="fa fa-user-circle user-avatar-placeholder" aria-hidden="true" />
        )
    ) : (
        <img src={displayPicture} alt="User" className="img_40_40 cursP" onError={() => setImageError(true)} onClick={() => setShowProfile ? setShowProfile(true) : null} />
    );
};

export default UserAvatar;
