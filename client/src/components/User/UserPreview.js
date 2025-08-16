import utils from "../../utils";
import './UserPreview.css';
import { useDispatch } from "react-redux";
import { showProfile } from "../../store/reducers/ProfileSlice";

const UserPreview = ({ user_detail }) => {

    const dispatch = useDispatch();
    const { userId, displayName, displayPicture, createdAt } = user_detail || {};

    return <div key={userId} className='user-preview FRCB w100'>
        <div className='FRCS'>
            <img src={displayPicture} alt={displayName} className='img_30_30 mR10' onClick={() => { dispatch(showProfile(userId)); }} />
            <h6>{displayName}</h6>
        </div>
        {createdAt && <span className='fs10 color777'>{utils.convertDateAndTime(createdAt)}</span>}
    </div>
}

export default UserPreview