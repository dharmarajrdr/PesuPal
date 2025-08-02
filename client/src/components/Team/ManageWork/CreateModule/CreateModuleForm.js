import { useState } from 'react';
import { apiRequest } from '../../../../http_request';
import { showPopup } from '../../../../store/reducers/PopupSlice';
import './CreateModuleForm.css';
import { useDispatch } from "react-redux";
import { useNavigate } from 'react-router-dom';

const CreateModuleForm = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();

    const [moduleName, setModuleName] = useState('');
    const [moduleDescription, setModuleDescription] = useState('');
    const [moduleType, setModuleType] = useState('ANYONE_IN_ORG');

    const validateInput = () => {
        if (!moduleName.trim()) {
            dispatch(showPopup({ message: 'Module name is required', type: 'error' }));
            return false;
        }
        if (moduleName.match(/[^a-zA-Z0-9\s]/)) {
            dispatch(showPopup({ message: 'Module name can only contain alphanumeric characters and spaces', type: 'error' }));
            return false;
        }
        if (moduleName.length < 3 || moduleName.length > 20) {
            dispatch(showPopup({ message: 'Module name must be between 3 and 20 characters', type: 'error' }));
            return false;
        }
        return true;
    }

    const cancelButtonHandler = () => {
        navigate('/manage/module');
    }

    const nextButtonHandler = () => {
        if (!validateInput()) return;
        apiRequest('/api/v1/module/create', "POST", {
            name: moduleName,
            description: moduleDescription,
            accessibility: moduleType
        }).then(({ message, data }) => {
            const { publicId } = data || {};
            navigate(`/manage/module/builder/${publicId}`);
            dispatch(showPopup({ message, type: 'success' }));
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    }

    return (
        <div id='create-module-form-container' className='w100'>
            <h3 id='title'>Create New Module</h3>
            <p id='description'>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>
            <span id='note-message'>Following data can be updatable later.</span>
            <div className='FCSS' id='create-module-form'>
                <div className='FRCS row mandatory-fields'>
                    <label htmlFor='moduleName'>Name</label>
                    <input type='text' id='moduleName' placeholder='Enter Module Name' value={moduleName} onChange={(e) => setModuleName(e.target.value)} />
                </div>
                <div className='FRCS row'>
                    <label htmlFor='moduleDescription'>Description</label>
                    <input id='moduleDescription' placeholder='Enter Module Description' value={moduleDescription} onChange={(e) => setModuleDescription(e.target.value)} />
                </div>
                <div className='FRCS row mandatory-fields'>
                    <label htmlFor='moduleType'> Accessibility</label>
                    <select id='moduleType' value={moduleType} onChange={(e) => setModuleType(e.target.value)}>
                        <option value='ANYONE_IN_ORG'>Anyone in the organization</option>
                        <option value='SELECTIVE_MEMBERS'>Selective members</option>
                        <option value='ONLY_ME'>Only me</option>
                    </select>
                </div>
                <div className='FRCE row mT20'>
                    <button id='cancel-button' className='mR10' onClick={cancelButtonHandler}>Cancel</button>
                    <button id='next-button' onClick={nextButtonHandler}>Next</button>
                </div>
            </div>
        </div>
    )
}

export default CreateModuleForm