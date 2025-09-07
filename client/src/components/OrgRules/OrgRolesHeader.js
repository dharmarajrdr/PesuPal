const OrgRolesHeader = ({ setShowNewRoleLayout, setShowManageRoles }) => {

    return <div id='org-roles-header' className='FRCB'>
        <h3>Roles & Permissions</h3>
        <div className="FRCE">
            <button id="manage-roles-button" onClick={() => setShowManageRoles(true)}>
                <i className='fa fa-cog w15 color777' aria-hidden='true'></i> Manage Roles
            </button>
            <button className="mL10" id="new-role-button" onClick={() => setShowNewRoleLayout(true)}>
                <i className='fa fa-plus w15 colorFFF' aria-hidden='true'></i> New Role
            </button>
        </div>
    </div>
}

export default OrgRolesHeader;