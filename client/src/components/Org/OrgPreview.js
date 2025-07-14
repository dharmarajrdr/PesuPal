import './OrgPreview.css';

const OrgPreview = ({ org, toggleOrgList }) => {

    const { id, active, displayName, role, uniqueName, displayPicture, members, status, subscription } = org;
    const { planName, expiresAt, status: subscriptionStatus } = subscription || {};
    const isOwner = role === 'ADMIN';
    const isTrial = planName == 'FREE_TRIAL';

    return (
        <div className={`FRCB org-preview p20 ${active ? 'active' : ''}`} key={id} onClick={toggleOrgList} >
            {isTrial && (
                <h5 className='trial-badge'>
                    TRIAL
                </h5>
            )}
            <div className='display-picture FCCC'>
                {displayPicture ?
                    <img src={displayPicture} alt='Logo' className='objectPositionCenter objectFitCover' /> :
                    <p>{uniqueName.trim().toUpperCase().charAt(0)}</p>
                }
            </div>
            <div className='FCSS org-details'>
                <div className='FRCB w100 mb5'>
                    <b className='org-displayname'>{displayName}</b>
                    <span className={`org-status ${subscriptionStatus.toLowerCase()}`}>
                        {subscriptionStatus}
                        <i className='fa fa-circle'></i>
                    </span>
                </div>
                <div className='FRCB w100'>
                    <span className='org-role'>
                        <i className={`fa ${isOwner ? 'fa-user-shield' : 'fa-user'}`}></i>
                        {role}
                    </span>
                    <span className='org-members-count'>
                        <i className="fa-solid fa-users"></i>
                        {members}
                    </span>

                </div>
            </div>
        </div>
    )
}

export default OrgPreview