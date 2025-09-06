import './ManageRoles.css';
import Loader from '../Loader';
import { useDispatch, useSelector } from "react-redux";
import { useEffect, useState } from 'react';
import { apiRequest } from '../../http_request';
import { showPopup } from '../../store/reducers/PopupSlice';
import { hideConfirmationPopup, showConfirmationPopup } from '../../store/reducers/ConfirmationPopupSlice';
import { deleteOrgRole, updateOrgRole } from '../../store/reducers/OrgRolePermissionsSlice';

const RolesList = ({ roles, currentRole, setCurrentRole }) => {

    return <div id='roles-list' className='FCSS'>
        {roles.map((role) => {
            const { name, roleId } = role || {};
            return <div key={roleId} className={`role-item ${currentRole?.roleId === role.roleId ? 'active' : ''}`} onClick={() => setCurrentRole(role)}>{name}</div>;
        })}
    </div>
}

const RoleDetailPlaceholder = () => {

    return <div className='placeholder FRCC w100 h100P'>
        <span className='fs12 colorAAA'>Select a role to view details</span>
    </div>
}

const FirstChar = ({ displayName }) => {

    return <div className='first-char-placeholder FRCC'>
        {displayName?.charAt(0)?.toUpperCase()}
    </div>
}

const RoleDetail = ({ currentRole, updateCurrentRole, deleteCurrentRole }) => {

    const dispatch = useDispatch();
    const { name, description, createdBy, memberCount } = currentRole || {};
    const [roleName, setRoleName] = useState('');
    const [roleDescription, setRoleDescription] = useState('');
    const { displayName, email, displayPicture } = createdBy || {};
    const isSuperAdmin = name === 'Super Admin';
    const [enableActiveChangesButton, setEnableActiveChangesButton] = useState(false);

    useEffect(() => {
        if (roleName.trim() !== name || roleDescription.trim() !== description) {
            setEnableActiveChangesButton(true);
        } else {
            setEnableActiveChangesButton(false);
        }
    }, [roleName, roleDescription]);

    useEffect(() => {
        setRoleName(name || '');
        setRoleDescription(description || '');
    }, [currentRole]);

    const updateRoleHandler = () => {
        const { roleId } = currentRole || {};
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to save changes?',
            options: [
                {
                    title: 'Save',
                    color: 'green',
                    onClick: () => {
                        apiRequest(`/api/v1/org-role/${roleId}`, 'PATCH', {
                            name: roleName.trim(),
                            description: roleDescription.trim()
                        }).then(({ data, message }) => {
                            dispatch(hideConfirmationPopup());
                            dispatch(showPopup({ message, type: 'success' }));
                            setEnableActiveChangesButton(false);
                            updateCurrentRole(data);
                        }).catch(({ message }) => {
                            dispatch(showPopup({ message, type: 'error' }));
                        });
                    }
                },
                {
                    title: 'Cancel',
                    color: 'gray',
                    onClick: () => dispatch(hideConfirmationPopup())
                }
            ]
        }));
    }

    const deleteCurrentRoleHandler = () => {

        const { roleId, memberCount } = currentRole || {};
        if (memberCount > 0) {
            return dispatch(showPopup({ message: `${memberCount} members are assigned to this role. Please reassign or remove them before deleting this role.`, type: 'error' }));
        }

        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to delete this role? This action cannot be undone.',
            options: [
                {
                    title: 'Delete',
                    color: 'red',
                    onClick: () => {
                        apiRequest(`/api/v1/org-role/${roleId}`, 'DELETE').then(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            dispatch(showPopup({ message, type: 'success' }));
                            deleteCurrentRole();
                        }).catch(({ message }) => {
                            dispatch(showPopup({ message, type: 'error' }));
                        });
                    }
                },
                {
                    title: 'Cancel',
                    color: 'gray',
                    onClick: () => dispatch(hideConfirmationPopup())
                }
            ]
        }));
    }

    return <div id='role-detail' className='FCSS w100'>
        {currentRole ? <div className='FCSB w100 h100P'>
            <div className='FCSS w100'>
                <div className='FRCS w100 row'>
                    <label>Name</label>
                    <input type='text' className='row-value' placeholder='Name' value={roleName} onChange={e => isSuperAdmin ? null : setRoleName(e.target.value)} />
                </div>
                <div className='FRCS w100 row'>
                    <label>Description</label>
                    <input type='text' className='row-value' placeholder='Description' value={roleDescription} onChange={e => isSuperAdmin ? null : setRoleDescription(e.target.value)} />
                </div>
                <div className='FRCS w100 row'>
                    <label>Created By</label>
                    <div className='FRCS row-value'>
                        <div className='avatar-placeholder'>
                            {displayPicture ? <img src={displayPicture} alt='Avatar' /> : <FirstChar displayName={displayName} />}
                        </div>
                        <div className='FCSS'>
                            <span className='display-name'>{displayName}</span>
                            <span className='email'>{email}</span>
                        </div>
                    </div>
                </div>
                <div className='FRCS w100 row'>
                    <label>Members</label>
                    <span className='row-value'>
                        <i className='fa fa-users w15 fs12 color777' aria-hidden='true'></i> {memberCount}
                    </span>
                </div>
            </div>
            {!isSuperAdmin && <div className='FRCB w100'>
                <button id='delete-role-button' className='FRCC' onClick={deleteCurrentRoleHandler}>
                    <i className='fa fa-trash fs12 w15' aria-hidden='true'></i> Delete Role
                </button>
                <button id='save-role-button' className={`FRCC ${enableActiveChangesButton ? '' : 'disabled'}`} onClick={updateRoleHandler}>
                    <i className='fa fa-save fs12 w15' aria-hidden='true'></i> Save Changes
                </button>
            </div>}
        </div> : <RoleDetailPlaceholder />}
    </div>
}

const ManageRoles = ({ onCloseManageRoles }) => {

    const onClose = (e) => {
        if (e.target.id === 'manage-roles-layout') {
            onCloseManageRoles();
        }
    }

    const dispatch = useDispatch();
    const { roles } = useSelector(state => state.orgRolePermissions);
    const [currentRole, setCurrentRole] = useState(null);
    const updateCurrentRole = (updatedRole) => {
        setCurrentRole(updatedRole);
        dispatch(updateOrgRole(updatedRole));
    }

    const deleteCurrentRole = () => {
        setCurrentRole(null);
        dispatch(deleteOrgRole(currentRole.roleId));
    }

    useEffect(() => {
        setCurrentRole(roles[0] || null);
    }, [roles]);

    return (
        <div className='entire-screen-overlay' id='manage-roles-layout' onClick={onClose}>
            <div id='manage-roles-content' className='centerMe FCSS'>
                <h3 className='w100'>Manage Roles</h3>
                <div id='manage-roles-body' className='w100 FRSS'>
                    <RolesList roles={roles} currentRole={currentRole} setCurrentRole={setCurrentRole} />
                    <RoleDetail currentRole={currentRole} updateCurrentRole={updateCurrentRole} deleteCurrentRole={deleteCurrentRole} />
                </div>
            </div>
        </div>
    )
}

export default ManageRoles