import { useRef, useState } from 'react';
import NewFolderLayout from './NewFolderLayout';
import { apiRequest } from '../../../http_request';
import { useDispatch, useSelector } from 'react-redux';
import AttachmentPreview from '../../Chat/AttachmentPreview';
import { addItem } from '../../../store/reducers/DriveSlice';
import { showPopup } from '../../../store/reducers/PopupSlice';

const maxFileSize = 10 * 1024 * 1024; // 10 MB

const HeaderActions = () => {

    const dispatch = useDispatch();
    const fileInputRef = useRef(null);
    const [files, setFiles] = useState([]);
    const [showCreateFolderModal, setShowCreateFolderModal] = useState(false);
    const { space: currentSpace, folderId: currentFolderId } = useSelector((state) => state.drive) || {};

    const handleFileChange = (e) => {

        const selectedFiles = Array.from(e.target.files);

        if (maxFileSize && selectedFiles.length + files.length > maxFileSize) {
            return dispatch(showPopup({ message: `You can only upload a maximum of ${maxFileSize} files.`, type: 'error' }));
        }

        if (maxFileSize && selectedFiles.some((file) => file.size > maxFileSize)) {
            return dispatch(showPopup({ message: `File size exceeds the maximum limit of ${maxFileSize / 1024 / 1024} MB.`, type: 'error' }));
        }

        const withPreview = selectedFiles.map((file) => ({
            file,
            'preview': file.type.startsWith("image") ? URL.createObjectURL(file) : null
        }));

        setFiles((prev) => [...prev, ...withPreview]);
        e.target.value = ""; // Reset file input
    };

    const uploadFilesClicked = () => {
        if (fileInputRef.current) {
            fileInputRef.current.click();
        }
    }

    const afterUpload = ({ media }) => {
        const { name, mediaId, extension, size } = media || {};
        const security = currentSpace.toUpperCase() == 'PERSONAL_SPACE' ? 'SECURED' : 'NONE';
        apiRequest(`/api/v1/workdrive/file`, 'POST', {
            name, security, mediaId, extension, size, "folderId": currentFolderId
        }).then(({ message, data }) => {
            Object.assign(data, { 'type': 'FILE' });
            dispatch(showPopup({ message, type: 'success' }));
            dispatch(addItem(data));
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    }

    return (
        <div className='FRCE'>
            {showCreateFolderModal && <NewFolderLayout onClose={() => { setShowCreateFolderModal(false); }} />}
            <button className='FRCC mR10' id='newFolderButton' onClick={() => setShowCreateFolderModal(true)}>
                <i className='fa fa-plus pR5 w_20'></i>
                <span>New Folder</span>
            </button>
            <AttachmentPreview files={files} setFiles={setFiles} afterUpload={afterUpload} />
            <input multiple type="file" ref={fileInputRef} style={{ display: 'none' }} onChange={handleFileChange} accept={"*/*"} />
            <button className='FRCC' id='uploadButton' onClick={uploadFilesClicked}>
                <i className='fa fa-upload pR5 w_20'></i>
                <span>Upload Files</span>
            </button>
        </div>
    )
}

export default HeaderActions