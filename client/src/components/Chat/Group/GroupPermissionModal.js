import { useEffect, useState } from 'react';
import Loader from '../../Loader';
import './GroupPermissionModal.css';
import { apiRequest } from '../../../http_request';

const Checked = () => <i className='fa fa-check checked' />;
const Crossed = () => <i className='fa fa-times crossed' />;

const PermissionColumn = ({ isChecked, name, setPermissions, role }) => {

    const onClick = (e) => {
        e.stopPropagation();
        e.preventDefault();
        setPermissions(prevPermissions =>
            prevPermissions.map(p => p.name === name ? { ...p, [role]: !isChecked } : p)
        );
    }

    return <td className='checkmark'>
        <span className={isChecked ? 'checked' : 'crossed'} onClick={onClick}>
            {isChecked ? <Checked /> : <Crossed />}
        </span>
    </td>
}

const PermissionRow = ({ permission, setPermissions, key }) => {

    const { name, superAdmin, admin, user } = permission || {};

    return <tr key={key}>
        <td>{name}</td>
        <PermissionColumn role={"superAdmin"} name={name} setPermissions={setPermissions} isChecked={superAdmin} />
        <PermissionColumn role={"admin"} name={name} setPermissions={setPermissions} isChecked={admin} />
        <PermissionColumn role={"user"} name={name} setPermissions={setPermissions} isChecked={user} />
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
        if (e.target.id === 'group-permission-modal') {
            onClose();
        }
    }

    return (
        <div id='group-permission-modal' className='entire-screen-overlay FRSE' onClick={overlayClickHandler}>
            <div id='group-permission-modal-content' className='FCCS'>
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
                                        <PermissionRow key={index} permission={permission} setPermissions={setPermissions} />
                                    ))}
                                </tbody>
                            </table>
                        </div> : null}
            </div>
        </div >
    )
}

export default GroupPermissionModal