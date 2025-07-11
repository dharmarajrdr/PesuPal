import React from 'react'
import OrgListDetails from './OrgListDetails'
import OrgPreview from './OrgPreview'
import './OrgList.css'

const OrgList = ({ toggleOrgList }) => {
    return (
        <div className='FCCS w100' id='org-list-modal'>
            {OrgListDetails.map((org, index) => <OrgPreview org={org} key={index} toggleOrgList={toggleOrgList} />)}
        </div>
    )
}

export default OrgList