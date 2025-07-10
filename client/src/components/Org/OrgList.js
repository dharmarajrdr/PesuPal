import React from 'react'
import OrgListDetails from './OrgListDetails'
import OrgPreview from './OrgPreview'

const OrgList = () => {
    return (
        <div className='FCCS w100'>
            {OrgListDetails.map((org, index) => <OrgPreview org={org} key={index} />)}
        </div>
    )
}

export default OrgList