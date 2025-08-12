import { useState } from 'react';
import './CreateGroupModal.css';
import { useDispatch } from "react-redux";
import { apiRequest } from '../../../http_request';
import GroupVisibility from './GroupVisibility';
import { showPopup } from '../../../store/reducers/PopupSlice';

const UpdateGroupModal = ({ setShowCreateGroupModal, groupData }) => {

    const dispatch = useDispatch();

    const { groupId, name, description, visibility } = groupData || {};
    const [groupName, setGroupName] = useState(name);
    const [groupDescription, setGroupDescription] = useState(description);
    const [isPublic, setIsPublic] = useState(visibility === 'PUBLIC');

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

        apiRequest(`/api/v1/group/${groupId}`, 'PATCH', groupData).then(({ data, message }) => {
            dispatch(showPopup({ message, type: 'success' }));
            console.log("Group updated successfully:", data);
            closeCreateGroupModal();
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
            closeCreateGroupModal();
        });
    };

    return (
        <div className="entire-screen-overlay" id='update-group-modal'>
            <div id="update-group-modal-content" className="centerMe FCCC modal-box">

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
