import { useSelector } from "react-redux";

const OrgRolesHeader = ({ setShowNewRoleLayout, setShowManageRoles }) => {

    const { hasPrivilegeToCreateOrgRole } = useSelector(state => state.orgRolePermissions) || {};

    return <div id='org-roles-header' className='FRCB'>
        <h3>Roles & Permissions</h3>
        <div className="FRCE">
            {hasPrivilegeToCreateOrgRole && <button className="mR10" id="new-role-button" onClick={() => setShowNewRoleLayout(true)}>
                <i className='fa fa-plus w15 colorFFF' aria-hidden='true'></i> New Role
            </button>}
            <button id="manage-roles-button" onClick={() => setShowManageRoles(true)}>
                <i className='fa fa-cog w15 color777' aria-hidden='true'></i> Manage Roles
            </button>
        </div>
    </div>
}

export default OrgRolesHeader;