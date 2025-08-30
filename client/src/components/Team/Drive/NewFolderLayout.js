import { useState } from 'react';
import './NewFolderLayout.css';
import { useDispatch, useSelector } from 'react-redux';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { apiRequest } from '../../../http_request';
import { useNavigate } from 'react-router-dom';
import { addItem } from '../../../store/reducers/DriveSlice';

const NewFolderLayout = ({ onClose }) => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [name, setsetName] = useState('');
    const [isSecured, setIsSecured] = useState(false);

    const { space: currentSpace, folderId: currentFolderId } = useSelector((state) => state.drive) || {};

    const createFolderHandler = () => {
        if (name.trim().length == 0) {
            return dispatch(showPopup({ message: 'Folder name is required', type: 'error' }));
        }
        if (!currentSpace) {
            dispatch(showPopup({ message: 'Something went wrong', type: 'error' }));
            return navigate('/store/personal_space');
        }
        apiRequest(`/api/v1/workdrive/folder`, 'POST', {
            name,
            space: currentSpace,
            'security': isSecured ? 'SECURED' : 'NONE',
            'parentFolderId': currentFolderId
        }).then(({ message, data }) => {
            Object.assign(data, { 'type': 'FOLDER' });
            dispatch(showPopup({ message, type: 'success' }));
            dispatch(addItem(data));
            onClose();
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    }

    return (
        <div id='new-folder-layout' className='entire-screen-overlay'>
            <div id='new-folder-container' className='centerMe FCSS p20'>
                <h2 className='w100'>Create New Folder</h2>
                <div className='FRCC w100'>
                    <input type='text' autoFocus placeholder='Folder Name' className='w100' value={name} onChange={(e) => setsetName(e.target.value)} />
                </div>
                <div className='FRCS w100'>
                    <input type='checkbox' id='secured' checked={isSecured} onChange={(e) => setIsSecured(e.target.checked)} />
                    <label className='fs14' htmlFor='secured'>Secured Folder</label>
                </div>
                <div className='FRCE w100 mT10'>
                    <button style={{ backgroundColor: 'gray' }} onClick={onClose}>Cancel</button>
                    <button className='mL10' onClick={createFolderHandler}>Create</button>
                </div>
            </div>
        </div>
    )
}

export default NewFolderLayout