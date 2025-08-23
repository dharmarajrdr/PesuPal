import { useState } from 'react'
import OptionsModal from '../../../Utils/OptionsModal'
import { useDispatch, useSelector } from 'react-redux';
import './ModuleBuilderHeader.css'
import MyModulesListOverlay from '../CreateModule/MyModulesListOverlay'
import { useNavigate, useParams } from 'react-router-dom';
import { apiRequest } from '../../../../http_request';
import { showPopup } from '../../../../store/reducers/PopupSlice';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../../store/reducers/ConfirmationPopupSlice';
import AddParticipantsModal from './AddParticipantsModal';
import { updateModuleData } from '../../../../store/reducers/CurrentModuleSlice';

const PublishButton = () => {
    return (
        <button id='publishButton'><i className='fa fa-paper-plane fs12 pR5 colorFFF'></i>Publish</button>
    )
}

const MoreOptionsButton = ({ moduleId }) => {

    const navigate = useNavigate();
    const dispatch = useDispatch();

    const deleteModuleHandler = () => {
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to delete this module? This action cannot be undone.',
            options: [
                {
                    title: 'Delete',
                    color: 'red',
                    onClick: () => {
                        apiRequest(`/api/v1/module/${moduleId}`, 'DELETE').then(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            dispatch(showPopup({ message, type: 'success' }));
                            navigate("/manage/module");
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
    }

    const options = [
        {
            icon: 'fa fa-plus',
            name: 'New Module',
            onClick: () => {
                navigate('/manage/module/create');
            }
        },
        {
            icon: 'fa fa-trash',
            name: 'Delete Module',
            onClick: deleteModuleHandler
        },
        {
            icon: 'fa fa-folder',
            name: 'My Modules',
            onClick: () => {
                closeOptions();
                setShowMyModulesList(true);
            }
        }
    ];

    const closeOptions = () => {
        setShowOptions(false);
    }

    const [showOptions, setShowOptions] = useState(false);
    const [showMyModulesList, setShowMyModulesList] = useState(false);

    return (
        <div id='more-options-button' className='FRCC mL10' onClick={() => setShowOptions(!showOptions)}>
            <i className='fa fa-ellipsis-vertical fs16'></i>
            {showOptions && <OptionsModal options={options} style={{ position: 'relative', top: '10px', right: '160px', width: '200px' }} />}
            {showMyModulesList && <MyModulesListOverlay onCloseModal={() => setShowMyModulesList(false)} />}
        </div>
    )
}

const ModuleMembersIcon = ({ memberCount, accessibility }) => {

    const [showAddParticipantsModal, setShowAddParticipantsModal] = useState(false);

    const { moduleId } = useParams();
    const isModuleAccessibleForSelectiveMembers = accessibility === 'SELECTIVE_MEMBERS';

    return isModuleAccessibleForSelectiveMembers && memberCount && (
        <div id='module-members-icon' className='FRCC' onClick={() => setShowAddParticipantsModal(true)}>
            {showAddParticipantsModal && <AddParticipantsModal moduleId={moduleId} onClose={() => setShowAddParticipantsModal(false)} />}
            <i className='fa fa-user w15 fs10'></i>
            <div id='module-member-count'>{memberCount}</div>
        </div>
    )
}

const ModuleVisibility = ({ accessibility, moduleId }) => {

    const dispatch = useDispatch();
    const [value, setValue] = useState(accessibility);

    const handleVisibilityChange = (e) => {

        const newValue = e.target.value;
        setValue(newValue);
        if (newValue == accessibility) return;

        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to update the visibility of this module?',
            options: [
                {
                    title: 'Update',
                    color: 'green',
                    onClick: () => {
                        apiRequest(`/api/v1/module/${moduleId}`, 'PATCH', {
                            'accessibility': newValue
                        }).then(({ message }) => {
                            dispatch(updateModuleData({ accessibility: newValue }));
                            dispatch(hideConfirmationPopup());
                            dispatch(showPopup({ message, type: 'success' }));
                        }).catch(({ message }) => {
                            dispatch(showPopup({ message, type: 'error' }));
                        });
                    }
                },
                {
                    title: 'Cancel',
                    color: 'gray',
                    onClick: () => {
                        dispatch(hideConfirmationPopup());
                        setValue(accessibility);
                    }
                }
            ]
        }));
    }

    return (
        <select value={value} onChange={handleVisibilityChange} id='module-visibility-select' className='mR10'>
            <option value="ONLY_ME">Only Me</option>
            <option value="SELECTIVE_MEMBERS">Selective Members</option>
            <option value="ANYONE_IN_ORG">Anyone in Org</option>
        </select>
    )
}

const ModuleBuilderHeader = () => {

    const module = useSelector(state => state.currentModule.data);
    const { publicId, name, description, memberCount, accessibility } = module || {};

    return (
        <div id='create-module-header' className='w100 FRCB'>
            <div className='FRCS'>
                <i className='fa fa-arrow-left fs16 mR10 w15' onClick={() => window.history.back()}></i>
                <h4 id='module-name'>{name}</h4>
                <i className='fa fa-info-circle fs12 mL10 w15 colorDDD' title={description || 'No description found'}></i>
                <ModuleMembersIcon memberCount={memberCount} accessibility={accessibility} />
            </div>
            <div className='FRCE'>
                <ModuleVisibility accessibility={accessibility} moduleId={publicId} />
                <PublishButton />
                <MoreOptionsButton moduleId={publicId} />
            </div>
        </div>
    )
}

export default ModuleBuilderHeader