import React, { useState } from 'react'
import OrgList from '../Org/OrgList'

const HomePageLayout = () => {

    const [showOrgList, setShowOrgList] = useState(false);
    const toggleOrgList = () => {
        setShowOrgList(!showOrgList);
    };
    return (
        <div id='home-page-layout'>
            <OrgList show={showOrgList} toggle={toggleOrgList} />
        </div>
    )
}

export default HomePageLayout