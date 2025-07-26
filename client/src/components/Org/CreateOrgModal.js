import { useState } from 'react';
import './CreateOrgModal.css';
import { apiRequest } from '../../http_request';
import { useNavigate } from 'react-router-dom';
import { showPopup } from '../../store/reducers/PopupSlice';
import { useDispatch } from 'react-redux';

const CreateOrgModal = () => {

    const [displayName, setDisplayName] = useState('Zoho CRM');
    const [uniqueName, setUniqueName] = useState('zohocrm');
    const [displayPicture, setDisplayPicture] = useState('');

    const navigate = useNavigate();
    const dispatch = useDispatch();

    const closeCreateOrgModal = () => {
        setDisplayName('');
        setUniqueName('');
        setDisplayPicture('');
        navigate("/");
    };

    const handleSubmit = () => {

        if (!displayName.trim() || !uniqueName.trim()) {
            alert("Display name and unique name are required.");
            return;
        }

        const orgData = {
            displayName: displayName.trim(),
            uniqueName: uniqueName.trim(),
            displayPicture: displayPicture.trim()
        };

        apiRequest('/api/v1/org', 'POST', orgData).then(({ data }) => {
            console.log(data);
            dispatch(showPopup({ message: `Organization created successfully!`, type: 'success' }));
            closeCreateOrgModal();
            navigate(`/`);
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    };

    return (
        <div id="create-org-modal" className="FCCC">
            <div id="create-org-modal-content" className="FCCC modal-box">
                <h2>Create Organization</h2>

                <div className='FCSS w100'>
                    <input
                        type="text"
                        placeholder="Display Name (e.g., Amazon)"
                        value={displayName}
                        onChange={(e) => setDisplayName(e.target.value)}
                        className="org-input"
                    />
                    <input
                        type="text"
                        placeholder="Unique Name (e.g., amazon)"
                        value={uniqueName}
                        onChange={(e) => setUniqueName(e.target.value)}
                        className="org-input"
                    />
                    <input
                        type="text"
                        placeholder="Display Picture URL (optional)"
                        value={displayPicture}
                        onChange={(e) => setDisplayPicture(e.target.value)}
                        className="org-input"
                    />
                </div>

                <div className='FRCC w100'>
                    <button className="submit-btn mR5" onClick={handleSubmit}>Create Org</button>
                    <button className="cancel-btn mL5" onClick={closeCreateOrgModal}>Cancel</button>
                </div>
            </div>
        </div>
    );
};

export default CreateOrgModal;
