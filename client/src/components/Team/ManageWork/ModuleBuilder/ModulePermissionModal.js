import '../../../Chat/Group/PermissionModal.css';
import { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { showPopup } from '../../../../store/reducers/PopupSlice';
import { apiRequest } from '../../../../http_request';
import Loader from '../../../Loader';

const Checked = () => <i className='fa fa-check checked' />;
const Crossed = () => <i className='fa fa-times crossed' />;

const PermissionColumn = ({ moduleId, isChecked, name, setPermissions, role, roleName }) => {

    const dispatch = useDispatch();

    const onClick = (e) => {
        e.stopPropagation();
        e.preventDefault();
        apiRequest(`/api/v1/module/permissions`, 'PATCH', {
            moduleId, role, name, 'enable': !isChecked
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

const PermissionRow = ({ moduleId, permission, setPermissions, key }) => {

    const { name, owner, maintainer, member } = permission || {};

    return <tr key={key}>
        <td>{name}</td>
        <PermissionColumn moduleId={moduleId} role={"OWNER"} roleName={"owner"} name={name} setPermissions={setPermissions} isChecked={owner} />
        <PermissionColumn moduleId={moduleId} role={"MAINTAINER"} roleName={"maintainer"} name={name} setPermissions={setPermissions} isChecked={maintainer} />
        <PermissionColumn moduleId={moduleId} role={"MEMBER"} roleName={"member"} name={name} setPermissions={setPermissions} isChecked={member} />
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

const ModulePermissionModal = ({ onClose, moduleId }) => {

    const [permissions, setPermissions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [noPermissionToAccess, setNoPermissionToAccess] = useState(false);

    useEffect(() => {

        apiRequest(`/api/v1/module/${moduleId}/permissions`, 'GET').then(({ data }) => {
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
                                        <th>Owner</th>
                                        <th>Maintainer</th>
                                        <th>Member</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {permissions.map((permission, index) => (
                                        <PermissionRow moduleId={moduleId} key={index} permission={permission} setPermissions={setPermissions} />
                                    ))}
                                </tbody>
                            </table>
                        </div>
                            : null}
            </div>
        </div >
    )
}

export default ModulePermissionModal