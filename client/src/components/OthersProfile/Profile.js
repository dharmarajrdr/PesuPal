import { Link } from 'react-router-dom'
import './Profile.css'
import { useEffect, useState } from 'react';
import { apiRequest } from '../../http_request';
import Loader from '../Loader';
import ErrorMessage from '../ErrorMessage';

const ContactInfo = ({ phone, email, Social }) => {
    return <div id='contact_info'>
        {/* <h4>User Information</h4> */}
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
        {Social?.length && <div className='FCSS w100 phone_email'>
            <label>Social</label>
            <div className='FRCS w100'>
                {Social.map(({ Name, icon, color, link }, index) =>
                    <Link target='_blank' to={link} key={index} >
                        <i title={Name} className={`social_accounts fa-brands ${icon}`} style={{ color }} />
                    </Link>
                )}
            </div>
        </div>}
    </div>
}

const NavigationLink = ({ to, icon, label, count, setShowProfile }) => {

    return <Link to={to} className='FRCC p10 navlink' onClick={() => setShowProfile(false)}>
        <i className={`fa ${icon} mR5`} />
        <h5>{label}</h5>
        {count !== undefined && <span className='fs12'>({count})</span>}
    </Link>
}

const Profile = ({ userId, setShowProfile }) => {

    const [profile, setProfile] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (userId == null) {
            setLoading(false);
            setError("No user ID provided");
            return;
        }
        apiRequest("/api/v1/profile/" + userId, "GET").then(({ data }) => {
            setLoading(false);
            setProfile(data);
        }).catch(({ message }) => {
            setLoading(false);
            setError(message);
        });
    }, []);

    const { displayName, designation, department, displayPicture, Social, phone, email, employeeId } = profile || {};

    const closeProfileOverlay = (e) => {
        if (e.target.id === 'ProfileOverlay') {
            setShowProfile(false);
        }
    }

    return (
        <div id='ProfileOverlay' className='FRCE' onClick={closeProfileOverlay}>
            <div id='ProfileCard' className='noScrollbar'>
                {loading ? <Loader /> :
                    error ? <ErrorMessage message={error} /> : <>
                        <div id='user_image_basic_info' className='FCCC'>
                            <img src={displayPicture} id='user_photo' className='objectFitCover objectPositionCenter' />
                            <div id='user_basic_info' className='FCCC'>
                                <div className='row'>
                                    <h4 id='profile_name'>{displayName}</h4>
                                </div>
                                <div className='row FRCC'>
                                    <p id='profile_designation' title="Designation">{designation}</p>
                                    {employeeId && <p className='employee_id' title="Employee Id">{employeeId}</p>}
                                </div>
                                <div className='row FRCC'>
                                    <i className='fa-solid fa-building mR5 colorAAA fs10'></i>
                                    <p id='profile_dept' title='Department'>{department}</p>
                                </div>
                                <div className='row mT10'>
                                    <i className='profile_contacts fa fa-comment' style={{ backgroundColor: 'blue' }} />
                                    <i className='profile_contacts fa fa-phone' style={{ backgroundColor: 'green' }} />
                                    <i className='profile_contacts fa fa-video' style={{ backgroundColor: 'red' }} />
                                </div>
                            </div>
                        </div>

                        <ContactInfo phone={phone} email={email} Social={Social} />

                        <div className='FRSS w100 navlinks'>
                            <NavigationLink to={`/people/${userId}/posts`} icon='fa-newspaper' label='Posts' count={45} setShowProfile={setShowProfile} />
                            <NavigationLink to={`/people/${userId}/file`} icon='fa-file' label='Files' count={12} setShowProfile={setShowProfile} />
                        </div>
                    </>
                }
            </div>
        </div>
    );
}

export default Profile