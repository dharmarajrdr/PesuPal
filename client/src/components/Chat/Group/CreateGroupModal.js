import { useState } from 'react';
import './CreateGroupModal.css';
import { apiRequest } from '../../../http_request';
import { useNavigate } from 'react-router-dom';

const Visibility = ({ isPublic, setIsPublic }) => {

    return <div className="visibility-toggle-container">
        <label className="toggle-label">Visibility</label>
        <div className="segmented-toggle">
            <div className={`toggle-option ${isPublic ? 'active' : ''}`} onClick={() => setIsPublic(true)}>
                <i className="fa fa-globe" />
                <span className='mL5'>Open to all</span>
            </div>
            <div className={`toggle-option ${!isPublic ? 'active' : ''}`} onClick={() => setIsPublic(false)}>
                <i className="fa fa-lock" />
                <span className='mL5'>Closed</span>
            </div>
            <div className={`toggle-bg ${isPublic ? 'left' : 'right'}`} />
        </div>
    </div>
}

const CreateGroupModal = ({ setShowCreateGroupModal }) => {

    const [groupName, setGroupName] = useState('');
    const [groupDescription, setGroupDescription] = useState('');
    const [isPublic, setIsPublic] = useState(true);

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

        apiRequest(`/api/v1/group/create`, 'POST', groupData).then(({ data }) => {
            const { id } = data;
            closeCreateGroupModal();
            navigate(`/chat/groups/${id}`);
        }).catch(({ message }) => {
            console.error("Error creating group:", message);
            closeCreateGroupModal();
        });
    };

    return (
        <div id="create-group-modal" className="FCCC">
            <div id="create-group-modal-content" className="FCCC modal-box">

                <h2>Create Group</h2>

                <div className='FCSS w100'>
                    <input type="text" placeholder="Enter group name" value={groupName} onChange={(e) => setGroupName(e.target.value)} className="group-name-input" />
                    <input type="text" placeholder="Enter group description (optional)" value={groupDescription} onChange={(e) => setGroupDescription(e.target.value)} className="group-description-input" />
                    <Visibility isPublic={isPublic} setIsPublic={setIsPublic} />
                </div>

                <div className='FRCC w100'>
                    <button className="submit-btn mR5" onClick={handleSubmit}>Create Group</button>
                    <button className="cancel-btn mL5" onClick={closeCreateGroupModal}>Cancel</button>
                </div>
            </div>
        </div>
    );
};

export default CreateGroupModal;
