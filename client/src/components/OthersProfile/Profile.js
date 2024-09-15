import React from 'react'
import { Link } from 'react-router-dom'
import './Profile.css'

const Profile = ({ Profile }) => {
    const { Name, Designation, Department, Contact, Social } = Profile || {},
        { Phone, Email } = Contact || {}, closeProfileOverlay = () => {
            const ProfileCardOverlay = document.getElementById('ProfileOverlay'),
                ProfileCard = document.getElementById('ProfileCard');
            ProfileCard.style.transition = 'transform 0.25s ease-in-out';
            ProfileCard.style.transform = 'translateX(100%)';
            const timer = setTimeout(() => {
                ProfileCardOverlay.style.display = 'none';
                clearTimeout(timer);
            }, 250);
        }

    return Profile ? (
        <div id='ProfileOverlay' className='FRCE'>
            <div id='ProfileCard' className='noScrollbar'>
                <i className="fa-solid fa-xmark" id='closeProfileOverlay' onClick={closeProfileOverlay}></i>
                <div id='user_image_basic_info' className='FCCC'>
                    <img src='/images/Users/user_10.jpg' id='user_photo' />
                    <div id='user_basic_info' className='FCCC'>
                        <div className='row'>
                            <h4 id='profile_name'>{Name}</h4>
                        </div>
                        <div className='row'>
                            <p id='profile_designation'>{Designation}</p>
                        </div>
                        <div className='row'>
                            <p id='profile_dept'>{Department}</p>
                        </div>
                        <div className='row mT10'>
                            <i className='profile_contacts fa fa-comment' style={{ backgroundColor: 'blue' }} />
                            <i className='profile_contacts fa fa-phone' style={{ backgroundColor: 'green' }} />
                            <i className='profile_contacts fa fa-video' style={{ backgroundColor: 'red' }} />
                        </div>
                    </div>
                </div>
                <div id='contact_info'>
                    <h4>User Information</h4>
                    <div className='FCSS w100 phone_email'>
                        <label>Phone</label>
                        <div className='FRCB w100'>
                            <b>{Phone}</b>
                            <i className='fa-regular fa-copy copy_contact_info' title='Copy' />
                        </div>
                    </div>
                    <div className='FCSS w100 phone_email'>
                        <label>Email</label>
                        <div className='FRCB w100'>
                            <b>{Email}</b>
                            <i className='fa-regular fa-copy copy_contact_info' title='Copy' />
                        </div>
                    </div>
                    {Social.length ?
                        <div className='FCSS w100 phone_email'>
                            <label>Social</label>
                            <div className='FRCS w100'>
                                {Social.map(({ Name, icon, color, link }, index) =>
                                    <Link target='_blank' to={link} key={index} >
                                        <i title={Name} className={`social_accounts fa-brands ${icon}`} style={{ color }} />
                                    </Link>
                                )}
                            </div>
                        </div>
                        : null}

                </div>
            </div>
        </div>
    ) : null;
}

export default Profile