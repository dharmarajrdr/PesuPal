import './OrgRolesLayout.css';
import { useState } from 'react';
import ManageRoles from './ManageRoles';
import OrgRolesTable from './OrgRolesTable';
import OrgRolesHeader from './OrgRolesHeader';
import NewRolesLayout from './NewRolesLayout';

const OrgRolesLayout = () => {

    const [showNewRoleLayout, setShowNewRoleLayout] = useState(false);
    const [showManageRoles, setShowManageRoles] = useState(false);

    const onCloseNewRoleLayout = () => {
        setShowNewRoleLayout(false);
    }

    const onCloseManageRoles = () => {
        setShowManageRoles(false);
    }

    return (
        <div id='org-roles-layout' className='w100'>
            <div id='org-roles-content'>
                <OrgRolesHeader setShowNewRoleLayout={setShowNewRoleLayout} setShowManageRoles={setShowManageRoles} />
                <OrgRolesTable />
                {showManageRoles && <ManageRoles onCloseManageRoles={onCloseManageRoles} />}
                {showNewRoleLayout && <NewRolesLayout onCloseNewRoleLayout={onCloseNewRoleLayout} />}
            </div>
        </div>
    )
}

export default OrgRolesLayout