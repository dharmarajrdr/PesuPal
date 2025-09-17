import './OrgPreview.css';
import { useState } from 'react';
import { useSelector } from 'react-redux';

const OrgPreview = ({ org, setCurrentOrg }) => {

    const { id, displayName, role, displayPicture, members, subscription } = org;
    const { planName, expiresAt, status: subscriptionStatus } = subscription || {};
    const { name: roleName } = role || {};
    const isOwner = roleName === 'Super Admin';
    const isTrial = planName == 'FREE_TRIAL';
    const { currentOrgId } = useSelector((state) => state.org);
    const active = org.publicId == currentOrgId;

    const orgClickHandler = (e) => {
        e.stopPropagation();
        if (!active) {
            setCurrentOrg(org);
        }
    };

    const [showDisplayPicture, setShowDisplayPicture] = useState(displayPicture);

    return (
        <div className={`FRCB org-preview p20 cursP ${active ? 'active' : ''}`} key={id} onClick={orgClickHandler} >
            {isTrial && <h5 className='trial-badge'>TRIAL</h5>}
            <div className='display-picture FCCC'>
                {showDisplayPicture ?
                    <img src={displayPicture} alt='Logo' onError={() => setShowDisplayPicture(false)} className='objectPositionCenter objectFitCover' /> :
                    <p>{displayName.trim().toUpperCase().charAt(0)}</p>
                }
            </div>
            <div className='FCSS org-details'>
                <div className='FRCB w100 mb5'>
                    <b className='org-displayname'>{displayName}</b>
                    <span className={`org-status ${subscriptionStatus.toLowerCase()}`}>
                        {subscriptionStatus}
                        <i className='fa fa-circle w15'></i>
                    </span>
                </div>
                <div className='FRCB w100'>
                    <span className='org-role'>
                        {roleName ? <>
                            <i className={`fa ${isOwner ? 'fa-user-shield' : 'fa-user'} w15`}></i>
                            {roleName}
                        </> : null}
                    </span>
                    <span className='org-members-count'>
                        <i className="fa-solid fa-users mR5"></i>
                        {members}
                    </span>

                </div>
            </div>
        </div>
    )
}

export default OrgPreview