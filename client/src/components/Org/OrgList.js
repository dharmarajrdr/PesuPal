import React from 'react'
import OrgListDetails from './OrgListDetails'
import OrgPreview from './OrgPreview'
import './OrgList.css'

const OrgList = ({ toggleOrgList, closeOrgList }) => {
    return (
        <div className='FCCS w100' id='org-list-modal' onClick={closeOrgList}>
            <div className='org-list-popup FCCB'>
                {OrgListDetails.map((org, index) => <OrgPreview org={org} key={index} toggleOrgList={toggleOrgList} />)}
            </div>
        </div>
    )
}

export default OrgList