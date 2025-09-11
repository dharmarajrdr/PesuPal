import Loader from "../Loader";
import { useEffect, useState } from "react";
import { apiRequest } from "../../http_request";
import { useDispatch, useSelector } from "react-redux";
import { showPopup } from "../../store/reducers/PopupSlice";
import { setHasPrivilegeToCreateOrgRole, setOrgRoles, setPermissions } from "../../store/reducers/OrgRolePermissionsSlice";

const Checked = () => <i className='fa fa-check checked' />;
const Crossed = () => <i className='fa fa-times crossed' />;

const NoPermissionsFound = () => {

    return (
        <div className='FCCC w100 h100P' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-lock w15 mR5' />
                No permissions found.
            </p>
        </div>
    )
}

const Header = ({ roles }) => {

    return <div id="org-roles-table-header" className="FRCS row">
        <div className="col">Actions</div>
        {roles.map(({ roleId, name }) => (
            <div key={roleId} className="col">
                <div className="FRCC">
                    <span className="role-name">{name}</span>
                    {/* <span className="pL5 fs10 colorDDD">(<i className="fa fa-users fs10 colorDDD w15"></i> 20202)</span> */}
                </div>
            </div>
        ))}
    </div>
}

const Body = ({ roles, permissions }) => {

    return <div id="org-roles-table-body" className="FCSS w100 h100P">
        {permissions.length ? permissions.map(permission => {

            const { action, roles: currentRoles } = permission || {};
            const { actionId, title } = action || {};
            const currentRolesNames = currentRoles.map(r => r.name);

            return <div key={actionId} className="FRCS row">
                <div className="col">{title}</div>
                {roles.map((role) => {
                    const { name, roleId } = role;
                    const allowed = currentRolesNames.includes(name);
                    return (
                        <Cell key={roleId} role={role} allowed={allowed} action={action} />
                    );
                })}
            </div>
        }) : <NoPermissionsFound />}
    </div>
}

const Cell = ({ role, action, allowed }) => {

    const dispatch = useDispatch();
    const { roleId, name: roleName } = role;
    const { actionId, title: actionTitle } = action;
    const [isAllowed, setIsAllowed] = useState(allowed);

    const updateConfigurationHandler = () => {
        const message = `Permission to "${actionTitle}" for role "${roleName}" has been ${isAllowed ? 'revoked' : 'granted'}.`;
        apiRequest(`/api/v1/org-configuration`, isAllowed ? 'DELETE' : 'POST', { roleId, actionId }).then(() => {
            setIsAllowed(!isAllowed);
            dispatch(showPopup({ message, type: 'success' }));
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    }

    return (
        <div key={roleId} className="col" onClick={updateConfigurationHandler}>
            {isAllowed ? <Checked /> : <Crossed />}
        </div>
    );
}

const OrgRolesTable = () => {

    const dispatch = useDispatch();
    const [loading, setLoading] = useState(true);
    const { permissions, roles } = useSelector(state => state.orgRolePermissions);

    useEffect(() => {
        apiRequest(`/api/v1/org-configuration`, 'GET').then(({ data, info }) => {
            const { permissions, roles } = data || {};
            permissions.sort((a, b) => a.action.actionId - b.action.actionId);
            dispatch(setPermissions(permissions));
            dispatch(setHasPrivilegeToCreateOrgRole(info.hasPrivilegeToCreateOrgRole));
            dispatch(setOrgRoles(roles));
            setLoading(false);
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
            setLoading(false);
        });
    }, []);

    return loading ? <Loader /> : <div className="FCSS w100 noScrollbar" id="org-roles-table">
        <Header roles={roles} />
        <Body roles={roles} permissions={permissions} />
    </div>
}

export default OrgRolesTable;