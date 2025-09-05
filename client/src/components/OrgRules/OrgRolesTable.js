const Checked = () => <i className='fa fa-check checked' />;
const Crossed = () => <i className='fa fa-times crossed' />;

const Header = ({ roles }) => {

    return <div id="org-roles-table-header" className="FRCS row">
        <div className="col">Actions</div>
        {roles.map(role => (
            <div key={role} className="col">
                <div className="FRCC">
                    <span className="role-name">{role}</span>
                    <span className="pL5 fs10 colorDDD">(<i className="fa fa-users fs10 colorDDD w15"></i> 20202)</span>
                </div>
            </div>
        ))}
    </div>
}

const Body = ({ roles, permissions }) => {

    return <div id="org-roles-table-body" className="FCSS w100">
        {permissions.map(permission => {

            const { action, roles: currentRoles } = permission;
            const currentRolesNames = currentRoles.map(r => r.name);

            return <div key={action.actionId} className="FRCS row">
                <div className="col">{action.title}</div>
                {roles.map((role) => {
                    const allowed = currentRolesNames.includes(role);
                    return (
                        <div key={role} className="col">
                            {allowed ? <Checked /> : <Crossed />}
                        </div>
                    );
                })}
            </div>
        })}
    </div>
}

const OrgRolesTable = () => {

    const roles = ["Super Admin", "Member"];

    const permissions = [
        {
            "action": {
                "actionId": 3,
                "title": "Update Member"
            },
            "roles": [
                {
                    "roleId": 14,
                    "name": "Super Admin",
                    "description": "Role with all permissions"
                }
            ]
        },
        {
            "action": {
                "actionId": 1,
                "title": "Add Member"
            },
            "roles": [
                {
                    "roleId": 14,
                    "name": "Super Admin",
                    "description": "Role with all permissions"
                }
            ]
        },
        {
            "action": {
                "actionId": 4,
                "title": "Leave Org"
            },
            "roles": [
                {
                    "roleId": 14,
                    "name": "Super Admin",
                    "description": "Role with all permissions"
                }
            ]
        },
        {
            "action": {
                "actionId": 9,
                "title": "Create Group"
            },
            "roles": [
                {
                    "roleId": 14,
                    "name": "Super Admin",
                    "description": "Role with all permissions"
                }
            ]
        },
        {
            "action": {
                "actionId": 6,
                "title": "Update Org"
            },
            "roles": [
                {
                    "roleId": 14,
                    "name": "Super Admin",
                    "description": "Role with all permissions"
                }
            ]
        },
        {
            "action": {
                "actionId": 8,
                "title": "Attach Media in Post"
            },
            "roles": [
                {
                    "roleId": 14,
                    "name": "Super Admin",
                    "description": "Role with all permissions"
                }
            ]
        },
        {
            "action": {
                "actionId": 2,
                "title": "Remove Member"
            },
            "roles": [
                {
                    "roleId": 14,
                    "name": "Super Admin",
                    "description": "Role with all permissions"
                }
            ]
        },
        {
            "action": {
                "actionId": 12,
                "title": "Update Department"
            },
            "roles": [
                {
                    "roleId": 14,
                    "name": "Super Admin",
                    "description": "Role with all permissions"
                }
            ]
        },
        {
            "action": {
                "actionId": 10,
                "title": "Create Role"
            },
            "roles": [
                {
                    "roleId": 14,
                    "name": "Super Admin",
                    "description": "Role with all permissions"
                }
            ]
        },
        {
            "action": {
                "actionId": 7,
                "title": "Create Post"
            },
            "roles": [
                {
                    "roleId": 14,
                    "name": "Super Admin",
                    "description": "Role with all permissions"
                }
            ]
        },
        {
            "action": {
                "actionId": 5,
                "title": "Delete Org"
            },
            "roles": [
                {
                    "roleId": 14,
                    "name": "Super Admin",
                    "description": "Role with all permissions"
                }
            ]
        },
        {
            "action": {
                "actionId": 13,
                "title": "Access Store"
            },
            "roles": [
                {
                    "roleId": 14,
                    "name": "Super Admin",
                    "description": "Role with all permissions"
                }
            ]
        },
        {
            "action": {
                "actionId": 11,
                "title": "Create Department"
            },
            "roles": [
                {
                    "roleId": 14,
                    "name": "Super Admin",
                    "description": "Role with all permissions"
                }
            ]
        }
    ]

    permissions.sort((a, b) => a.action.actionId - b.action.actionId);

    return <div className="FCSS w100 noScrollbar" id="org-roles-table">
        <Header roles={roles} />
        <Body roles={roles} permissions={permissions} />
    </div>
}

export default OrgRolesTable;