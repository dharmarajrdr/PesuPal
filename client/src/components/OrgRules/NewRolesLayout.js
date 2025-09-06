import './NewRolesLayout.css';
import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { apiRequest } from '../../http_request';
import { showPopup } from '../../store/reducers/PopupSlice';
import { addNewRole } from '../../store/reducers/OrgRolePermissionsSlice';
import { hideConfirmationPopup, showConfirmationPopup } from '../../store/reducers/ConfirmationPopupSlice';

const NewRolesLayout = ({ onCloseNewRoleLayout }) => {

    const dispatch = useDispatch();
    const [roleName, setRoleName] = useState('');
    const [roleDescription, setRoleDescription] = useState('');

    const onValidInput = (callback) => {
        if (roleName.trim() === '') {
            return dispatch(showPopup({ message: 'Role name cannot be empty', type: 'error' }));
        }
        callback();
    }

    const onCreateRole = () => {

        onValidInput(() => {
            dispatch(showConfirmationPopup({
                message: 'Are you sure you want to create this role?',
                options: [
                    {
                        title: 'Create',
                        color: 'green',
                        onClick: () => {
                            apiRequest(`/api/v1/org-role`, 'POST', {
                                name: roleName,
                                description: roleDescription
                            }).then(({ message }) => {
                                dispatch(hideConfirmationPopup());
                                dispatch(showPopup({ message, type: 'success' }));
                                onCloseNewRoleLayout();
                                dispatch(addNewRole(roleName));
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
        });
    }


    return (
        <div className='entire-screen-overlay'>
            <div id='new-roles-content' className='centerMe'>
                <h1 id='title'>Create Role</h1>
                <div className='row'>
                    <input type='text' value={roleName} onChange={(e) => setRoleName(e.target.value)} placeholder='Name' />
                </div>
                <div className='row'>
                    <input type='text' value={roleDescription} onChange={(e) => setRoleDescription(e.target.value)} placeholder='Description' />
                </div>
                <div className='row FRCE mT5'>
                    <button id='cancel-role-button' onClick={onCloseNewRoleLayout}>Cancel</button>
                    <button id='create-role-button' className='mL10' onClick={onCreateRole}>Create</button>
                </div>
            </div>
        </div>
    )
}

export default NewRolesLayout