import { Link } from 'react-router-dom'
import './Profile.css'
import { useEffect, useState } from 'react';
import { apiRequest } from '../../http_request';
import Loader from '../Loader';
import ErrorMessage from '../ErrorMessage';

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
        apiRequest("/api/v1/people/" + userId, "GET").then(({ data }) => {
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
                        <div id='contact_info'>
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
                    </>
                }
            </div>
        </div>
    );
}

export default Profile