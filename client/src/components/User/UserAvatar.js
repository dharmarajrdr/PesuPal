import { useState } from "react";
import './UserAvatar.css';
import utils from "../../utils";
import { useDispatch } from "react-redux";
import { showProfile } from "../../store/reducers/ProfileSlice";

const UserAvatar = ({ displayPicture, displayName, userId }) => {

    const dispatch = useDispatch();
    const [imageError, setImageError] = useState(false);

    const showUserProfile = () => { dispatch(showProfile(userId)); }

    return (imageError || !displayPicture) ? (
        displayName ? (
            <p className="user-avatar-placeholder-first-character img_40_40" style={{ backgroundColor: utils.uniqueColorGenerator(displayName) }} onClick={showUserProfile}>
                {displayName.charAt(0).toUpperCase()}
            </p>
        ) : (
            <i className="fa fa-user-circle user-avatar-placeholder" aria-hidden="true" />
        )
    ) : (
        <img src={displayPicture} alt="User" className="img_40_40 cursP objectFitCover" onError={() => setImageError(true)} onClick={showUserProfile} />
    );
};

export default UserAvatar;
