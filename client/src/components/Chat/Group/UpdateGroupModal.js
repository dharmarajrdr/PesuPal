import { useState } from 'react';
import './CreateGroupModal.css';
import { apiRequest } from '../../../http_request';
import { useNavigate } from 'react-router-dom';
import GroupVisibility from './GroupVisibility';

const UpdateGroupModal = ({ setShowCreateGroupModal, groupData }) => {

    const [groupName, setGroupName] = useState(groupData.name);
    const [groupDescription, setGroupDescription] = useState(groupData.description);
    const [isPublic, setIsPublic] = useState(groupData.visibility === 'PUBLIC');

    const navigate = useNavigate();

    const closeCreateGroupModal = () => {
        setGroupName('');
        setGroupDescription('');
        setIsPublic(true);
        setShowCreateGroupModal(false);
    }

    const handleSubmit = () => {
        if (!groupName.trim()) {
            alert("Group name can't be empty!");
            return;
        }
        const groupData = {
            name: groupName.trim(),
            description: groupDescription.trim(),
            visibility: isPublic ? 'PUBLIC' : 'PRIVATE',
        };

        apiRequest(`/api/v1/group/update`, 'PATCH', groupData).then(({ data }) => {
            const { id } = data;
            closeCreateGroupModal();
        }).catch(({ message }) => {
            console.error("Error updating group:", message);
            closeCreateGroupModal();
        });
    };

    return (
        <div className="FCCC entire-screen-overlay">
            <div id="update-group-modal-content" className="FCCC modal-box">

                <h2>Update Group</h2>

                <div className='FCSS w100'>
                    <input type="text" placeholder="Enter group name" value={groupName} onChange={(e) => setGroupName(e.target.value)} className="group-name-input" />
                    <input type="text" placeholder="Enter group description (optional)" value={groupDescription} onChange={(e) => setGroupDescription(e.target.value)} className="group-description-input" />
                    <GroupVisibility isPublic={isPublic} setIsPublic={setIsPublic} />
                </div>

                <div className='FRCC w100'>
                    <button className="cancel-btn mR5" onClick={closeCreateGroupModal}>Cancel</button>
                    <button className="submit-btn mL5" onClick={handleSubmit}>Update</button>
                </div>
            </div>
        </div>
    );
};

export default UpdateGroupModal;
