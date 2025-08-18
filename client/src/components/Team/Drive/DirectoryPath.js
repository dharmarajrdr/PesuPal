import './DirectoryPath.css';
import { useState } from 'react';
import { apiRequest } from '../../../http_request';
import OptionsModal from '../../Utils/OptionsModal';
import { useDispatch, useSelector } from 'react-redux';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';
import { setItems } from '../../../store/reducers/DriveSlice';

const Item = ({ id, name, security, space }) => {

    const route = `/store/${space.toLowerCase()}${id ? `/folder/${id}` : ''}`;

    return <Link to={route} className='FRCC directory-list-item'>
        {security === 'SECURED' && <i className='fa fa-lock fs10 color555 w15'></i>}
        <span>{name}</span>
    </Link>
};

const DirectoryPath = () => {

    const params = useParams();
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const { space, folderId } = params || {};
    const [showOptions, setShowOptions] = useState(false);
    const { parents: directories } = useSelector((state) => state.drive) || [];

    const options = [
        {
            name: `View Details`,
            icon: `fa fa-info-circle`,
            onClick: (e) => {

            }
        },
        {
            name: `Manage Access`,
            icon: `fa fa-users`,
            onClick: (e) => {

            }
        },
        {
            name: `Clear Folder`,
            icon: `fa fa-eraser`,
            onClick: (e) => {
                dispatch(showConfirmationPopup({
                    message: 'Are you sure you want to clear all files and folders in this directory?',
                    options: [
                        {
                            title: 'Delete',
                            color: 'red',
                            onClick: () => {
                                apiRequest(`/api/v1/workdrive/clear/${folderId}`, 'DELETE').then(({ message }) => {
                                    dispatch(setItems([]));
                                    setShowOptions(false);
                                    dispatch(hideConfirmationPopup());
                                    dispatch(showPopup({ message, type: 'success' }));
                                }).catch(({ message }) => {
                                    setShowOptions(false);
                                    dispatch(hideConfirmationPopup());
                                    dispatch(showPopup({ message, type: 'error' }));
                                });
                            }
                        },
                        {
                            title: 'Cancel',
                            color: 'gray',
                            onClick: () => {
                                dispatch(hideConfirmationPopup());
                            }
                        }
                    ]
                }));
            }
        },
        {
            name: `Delete Folder`,
            icon: `fa fa-trash`,
            onClick: (e) => {
                dispatch(showConfirmationPopup({
                    message: 'Are you sure you want to delete this folder?',
                    options: [
                        {
                            title: 'Delete',
                            color: 'red',
                            onClick: () => {
                                apiRequest(`/api/v1/workdrive/folder/${folderId}`, 'DELETE').then(({ message }) => {
                                    let route = `/store/${space.toLowerCase()}`;
                                    if (directories.length > 2) {
                                        const parent = directories[directories.length - 2];
                                        const { id: parentId } = parent || {};
                                        route += `/folder/${parentId}`;
                                    }
                                    navigate(route);
                                    setShowOptions(false);
                                    dispatch(hideConfirmationPopup());
                                    dispatch(showPopup({ message, type: 'success' }));
                                }).catch(({ message }) => {
                                    setShowOptions(false);
                                    dispatch(hideConfirmationPopup());
                                    dispatch(showPopup({ message, type: 'error' }));
                                });
                            }
                        },
                        {
                            title: 'Cancel',
                            color: 'gray',
                            onClick: () => {
                                dispatch(hideConfirmationPopup());
                            }
                        }
                    ]
                }));
            }
        }
    ]

    return (
        <div className='FRCB w100 pR' id='directory-path'>
            <i className='fa fa-folder mL10 fs20 color555'></i>
            <div className='FRCS noScrollbar' id='directory-list' >
                {directories.map(({ id, name, security, space }) => (
                    <Item key={id} id={id} name={name} security={security} space={space} />
                ))}
            </div>
            {showOptions && <OptionsModal options={options} style={{ right: '0px', top: '35px' }} />}
            <i className='fa fa-gear' id='directory-settings' onClick={() => setShowOptions(!showOptions)}></i>
        </div>
    )
}

export default DirectoryPath