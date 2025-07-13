import { Link } from 'react-router-dom'
import './Profile.css'

const Profile = ({ Profile, setShowProfile }) => {

    const { displayName, designation, department, displayPicture, Social, phone, email } = Profile || {};

    return Profile ? (
        <div id='ProfileOverlay' className='FRCE' onClick={() => { setShowProfile(false) }}>
            <div id='ProfileCard' className='noScrollbar'>
                <i className="fa-solid fa-xmark" id='closeProfileOverlay'></i>
                <div id='user_image_basic_info' className='FCCC'>
                    <img src={displayPicture} id='user_photo' />
                    <div id='user_basic_info' className='FCCC'>
                        <div className='row'>
                            <h4 id='profile_name'>{displayName}</h4>
                        </div>
                        <div className='row'>
                            <p id='profile_designation'>{designation}</p>
                        </div>
                        <div className='row'>
                            <p id='profile_dept'>{department}</p>
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
                            <b>{phone}</b>
                            <i className='fa-regular fa-copy copy_contact_info' title='Copy' />
                        </div>
                    </div>
                    <div className='FCSS w100 phone_email'>
                        <label>Email</label>
                        <div className='FRCB w100'>
                            <b>{email}</b>
                            <i className='fa-regular fa-copy copy_contact_info' title='Copy' />
                        </div>
                    </div>
                    {Social?.length ?
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