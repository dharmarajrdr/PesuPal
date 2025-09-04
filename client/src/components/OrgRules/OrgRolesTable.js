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
        {permissions.map(permission => (
            <div key={permission.name} className="FRCS row">
                <div className="col">{permission.name}</div>
                {roles.map((role) => {
                    const allowed = permission.roles.includes(role);
                    return (
                        <div key={role} className="col">
                            {allowed ? <Checked /> : <Crossed />}
                        </div>
                    );
                })}
            </div>
        ))}
    </div>
}

const OrgRolesTable = () => {

    const roles = ["Super Admin", "Admin", "Member", "Guest", "External"];

    const permissions = [
        {
            name: "Create Project",
            roles: ["Super Admin", "Admin"]
        },
        {
            name: "Delete Project",
            roles: ["Super Admin"]
        }
    ]

    return <div className="FCSS w100 noScrollbar" id="org-roles-table">
        <Header roles={roles} />
        <Body roles={roles} permissions={permissions} />
    </div>
}

export default OrgRolesTable;