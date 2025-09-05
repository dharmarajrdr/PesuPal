const Checked = () => <i className='fa fa-check checked' />;
const Crossed = () => <i className='fa fa-times crossed' />;

const Header = ({ roles }) => {

    return <div id="org-roles-table-header" className="FRCS row">
        <div className="col">Actions</div>
        {roles.map(role => (
            <div key={role} className="col">
                <div className="FRCC">
                    <span className="role-name">{role}</span>
                    <span className="pL5 fs10 color555">(<i className="fa fa-users fs10 color555 w15"></i> 20202)</span>
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

    const roles = ["Super Admin", "Admin", "Member", "Guest", "External"];

    const permissions = [
        {
            "action": {
                "actionId": 6,
                "title": "Update Org"
            },
            "roles": [
                {
                    "roleId": 1,
                    "name": "Super Admin"
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
                    "roleId": 1,
                    "name": "Super Admin"
                },
                {
                    "roleId": 2,
                    "name": "Member"
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
                    "roleId": 1,
                    "name": "Super Admin"
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