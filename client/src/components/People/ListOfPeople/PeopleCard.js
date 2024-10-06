import React from 'react'
import { Link } from 'react-router-dom';
import './PeopleCard.css';
import { StatusIndicator } from '../../Auth/utils';

const PeopleCard = ({ person }) => {

    const { name, profile_image, department, status, id } = person;
    return (
        <div className='FCCC PeopleCard'>
            <i className="fa fa-ellipsis-vertical three_dots"></i>
            <div className='FCCC'>
                <div className='FRCC profile_picture_container'>
                    <img src={profile_image} className='img_75_75 mB10' />
                    <StatusIndicator status={status} />
                </div>
                <b className='mx5 user_name'>{name}</b>
                <span className='color777 fs10'>{department}</span>
            </div>
            <div className='mx10 FRCC'>
                <i className='profile_contacts fa fa-comment' style={{ backgroundColor: 'blue' }} />
                <i className='profile_contacts fa fa-phone' style={{ backgroundColor: 'green' }} />
                <i className='profile_contacts fa fa-video' style={{ backgroundColor: 'red' }} />
            </div>
            <div className='mT5'>
                <Link to={"/user/" + id} className='view_profile_button'>View Profile</Link>
            </div>

        </div>
    )
}

export default PeopleCard