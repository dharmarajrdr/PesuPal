import './OrgRolesLayout.css';
import { useState } from 'react';
import OrgRolesTable from './OrgRolesTable';
import OrgRolesHeader from './OrgRolesHeader';
import NewRulesLayout from './NewRulesLayout';

const OrgRolesLayout = () => {

    const [showNewRoleLayout, setShowNewRoleLayout] = useState(false);

    const onCloseNewRoleLayout = () => {
        setShowNewRoleLayout(false);
    }

    return (
        <div id='org-roles-layout' className='w100'>
            <div id='org-roles-content'>
                <OrgRolesHeader setShowNewRoleLayout={setShowNewRoleLayout} />
                <OrgRolesTable />
                {showNewRoleLayout && <NewRulesLayout onCloseNewRoleLayout={onCloseNewRoleLayout} />}
            </div>
        </div>
    )
}

export default OrgRolesLayout