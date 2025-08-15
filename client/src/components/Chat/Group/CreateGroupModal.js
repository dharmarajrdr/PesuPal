import { useState } from 'react';
import './CreateGroupModal.css';
import { apiRequest } from '../../../http_request';
import { useNavigate } from 'react-router-dom';
import GroupVisibility from './GroupVisibility';
import ImageUploader from '../../ImageUploader';
import { useDispatch } from 'react-redux';
import { showPopup } from '../../../store/reducers/PopupSlice';
import Media from '../../../Media';

const CreateGroupModal = ({ setShowCreateGroupModal }) => {

    const dispatch = useDispatch();
    const [groupName, setGroupName] = useState('');
    const [groupDescription, setGroupDescription] = useState('');
    const [isPublic, setIsPublic] = useState(true);
    const [file, setFile] = useState(null);

    const navigate = useNavigate();

    const closeCreateGroupModal = () => {
        setGroupName('');
        setGroupDescription('');
        setIsPublic(true);
        setShowCreateGroupModal(false);
    }

    const handleSubmit = () => {
        if (!groupName.trim()) {
            dispatch(showPopup({ message: "Group name can't be empty!", type: 'error' }));
            return;
        }
        const groupData = {
            name: groupName.trim(),
            description: groupDescription.trim(),
            visibility: isPublic ? 'PUBLIC' : 'PRIVATE',
        };

        const createGroup = async (groupData) => {

            apiRequest(`/api/v1/group/create`, 'POST', groupData).then(({ data, message }) => {
                const { id } = data;
                closeCreateGroupModal();
                navigate(`/chat/groups/${id}`);
                dispatch(showPopup({ message, type: 'success' }));
            }).catch(({ message }) => {
                dispatch(showPopup({ message, type: 'error' }));
            });
        }

        if (file == null) {
            createGroup(groupData);
        } else {
            Media.uploadSingleMedia({ file }).then(({ data }) => {
                const { mediaId, extension } = data || {};
                Object.assign(groupData, { 'displayPicture': `${mediaId}.${extension}` });
                createGroup(groupData);
            }).catch(({ message }) => {
                dispatch(showPopup({ message, type: 'error' }));
            });
        }

    };

    return (
        <div className="FCCC entire-screen-overlay">
            <div id="create-group-modal-content" className="FCCC modal-box">

                <h2>Create Group</h2>

                <div className='FCSS w100'>
                    <ImageUploader onImageSelect={(file) => setFile(file)} style={{ width: '100px', height: '100px', marginBottom: '20px' }} />
                    <input type="text" placeholder="Enter group name" value={groupName} onChange={(e) => setGroupName(e.target.value)} className="group-name-input" />
                    <input type="text" placeholder="Enter group description (optional)" value={groupDescription} onChange={(e) => setGroupDescription(e.target.value)} className="group-description-input" />
                    <GroupVisibility isPublic={isPublic} setIsPublic={setIsPublic} />
                </div>
                <div className='FRCC w100'>
                    <button className="cancel-btn mR5" onClick={closeCreateGroupModal}>Cancel</button>
                    <button className="submit-btn mL5" onClick={handleSubmit}>Create</button>
                </div>
            </div>
        </div>
    );
};

export default CreateGroupModal;
