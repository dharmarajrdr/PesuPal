import './PermissionModal.css';
import Loader from '../../Loader';
import { useDispatch } from 'react-redux';
import { useEffect, useState } from 'react';
import { apiRequest } from '../../../http_request';
import { showPopup } from '../../../store/reducers/PopupSlice';

const Checked = () => <i className='fa fa-check checked' />;
const Crossed = () => <i className='fa fa-times crossed' />;

const PermissionColumn = ({ groupId, isChecked, name, setPermissions, role, roleName }) => {

    const dispatch = useDispatch();

    const onClick = (e) => {
        e.stopPropagation();
        e.preventDefault();
        apiRequest(`/api/v1/group-chat-configuration`, 'PATCH', {
            groupId, role, name, enable: !isChecked
        }).then(({ message }) => {
            setPermissions(prevPermissions =>
                prevPermissions.map(p => p.name === name ? { ...p, [roleName]: !isChecked } : p)
            );
            dispatch(showPopup({ message, type: 'success' }));
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    }

    return <td className='checkmark'>
        <span className={isChecked ? 'checked' : 'crossed'} onClick={onClick}>
            {isChecked ? <Checked /> : <Crossed />}
        </span>
    </td>
}

const PermissionRow = ({ groupId, permission, setPermissions, key }) => {

    const { name, superAdmin, admin, user } = permission || {};

    return <tr key={key}>
        <td>{name}</td>
        <PermissionColumn groupId={groupId} role={"SUPER_ADMIN"} roleName={"superAdmin"} name={name} setPermissions={setPermissions} isChecked={superAdmin} />
        <PermissionColumn groupId={groupId} role={"ADMIN"} roleName={"admin"} name={name} setPermissions={setPermissions} isChecked={admin} />
        <PermissionColumn groupId={groupId} role={"USER"} roleName={"user"} name={name} setPermissions={setPermissions} isChecked={user} />
    </tr>
}


const NoPermissionToAccess = () => {

    return (
        <div className='FCCC w100 h100' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-lock mR5' />
                You don't have permission to access.
            </p>
        </div>
    )
}

const GroupPermissionModal = ({ onClose, groupId }) => {

    const [permissions, setPermissions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [noPermissionToAccess, setNoPermissionToAccess] = useState(false);

    useEffect(() => {

        apiRequest(`/api/v1/group-chat-configuration/${groupId}`, 'GET').then(({ data }) => {
            setLoading(false);
            setPermissions(data);
        }).catch(({ error }) => {
            setLoading(false);
            setNoPermissionToAccess(true);
        });
    }, []);

    const overlayClickHandler = (e) => {
        e.stopPropagation();
        e.preventDefault();
        if (e.target.id === 'permission-modal') {
            onClose();
        }
    }

    return (
        <div id='permission-modal' className='entire-screen-overlay FRSE' onClick={overlayClickHandler}>
            <div id='permission-modal-content' className='FCCS'>
                <h4 id='title'>Permissions</h4>
                {loading ? <Loader /> :
                    noPermissionToAccess ? <NoPermissionToAccess /> :
                        permissions.length ? <div className='w100'>
                            <table id='permissions-table' cellPadding={0} cellSpacing={0}>
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th>Super Admin</th>
                                        <th>Admin</th>
                                        <th>Member</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {permissions.map((permission, index) => (
                                        <PermissionRow groupId={groupId} key={index} permission={permission} setPermissions={setPermissions} />
                                    ))}
                                </tbody>
                            </table>
                        </div>
                            : null}
            </div>
        </div >
    )
}

export default GroupPermissionModal