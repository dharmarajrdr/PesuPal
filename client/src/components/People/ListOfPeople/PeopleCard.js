import './PeopleCard.css';
import { StatusIndicator } from '../../Auth/utils';

const PeopleCard = ({ person }) => {

    const { displayName, displayPicture, designation, status, id } = person;
    return (
        <div className='FCCC PeopleCard'>
            <i className="fa fa-ellipsis-vertical three_dots"></i>
            <div className='FCCC mB5 img_name_dept'>
                <div className='FRCC profile_picture_container mB10'>
                    <img src={displayPicture} className='img_75_75' />
                    <StatusIndicator status={status} />
                </div>
                <b className='mx5 user_name'>{displayName}</b>
                <span className='color777 fs10 mB5'>{designation}</span>
            </div>
            <div className='mT5 FRCC'>
                <i className='profile_contacts fa fa-comment' style={{ backgroundColor: 'blue' }} />
                <i className='profile_contacts fa fa-phone' style={{ backgroundColor: 'green' }} />
                <i className='profile_contacts fa fa-video' style={{ backgroundColor: 'red' }} />
            </div>
        </div>
    )
}

export default PeopleCard