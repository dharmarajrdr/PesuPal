import { useState } from 'react'
import OptionsModal from '../../../Utils/OptionsModal'
import { useDispatch, useSelector } from 'react-redux';
import './ModuleBuilderHeader.css'
import MyModulesListOverlay from '../CreateModule/MyModulesListOverlay'
import ConfirmationPopup from '../../../Utils/ConfirmationPopup';
import { useNavigate } from 'react-router-dom';
import { apiRequest } from '../../../../http_request';
import { showPopup } from '../../../../store/reducers/PopupSlice';

const PublishButton = () => {
    return (
        <button id='publishButton'><i className='fa fa-paper-plane fs12 pR5 colorFFF'></i>Publish</button>
    )
}

const MoreOptionsButton = ({ moduleId }) => {

    const navigate = useNavigate();
    const dispatch = useDispatch();

    const deleteModuleHandler = () => {
        setConfirmationData({
            message: 'Are you sure you want to delete this module? This action cannot be undone.',
            options: [
                {
                    title: 'Delete',
                    color: 'red',
                    onClick: () => {
                        apiRequest(`/api/v1/module/${moduleId}`, 'DELETE').then(({ message }) => {
                            setShowConfirmationPopup(false);
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
                    onClick: () => setShowConfirmationPopup(false)
                }
            ]
        });
        setShowConfirmationPopup(true);
    }

    const options = [
        {
            icon: 'fa fa-plus',
            name: 'New Module',
            onClick: () => console.log('New Module clicked')
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

    const [showConfirmationPopup, setShowConfirmationPopup] = useState(false);
    const [confirmationData, setConfirmationData] = useState({ 'message': null, 'options': [] });
    const [showOptions, setShowOptions] = useState(false);
    const [showMyModulesList, setShowMyModulesList] = useState(false);

    return (
        <div id='more-options-button' className='FRCC mL10' onClick={() => setShowOptions(!showOptions)}>
            <i className='fa fa-ellipsis-vertical fs16'></i>
            {showOptions && <OptionsModal options={options} style={{ position: 'relative', top: '10px', right: '160px', width: '200px' }} />}
            {showMyModulesList && <MyModulesListOverlay onCloseModal={() => setShowMyModulesList(false)} />}
            {showConfirmationPopup && <ConfirmationPopup onClose={() => setShowConfirmationPopup(false)} message={confirmationData.message} options={confirmationData.options} />}
        </div>
    )
}

const ModuleBuilderHeader = () => {

    const module = useSelector(state => state.currentModule.data);
    const { publicId, name, description } = module || {};

    return (
        <div id='create-module-header' className='w100 FRCB'>
            <div className='FRCS'>
                <i className='fa fa-arrow-left fs16 mR10' onClick={() => window.history.back()}></i>
                <h4 id='module-name'>{name}</h4>
                <i className='fa fa-info-circle fs12 mL10 colorDDD' title={description || 'No description found'}></i>
            </div>
            <div className='FRCE'>
                <PublishButton />
                <MoreOptionsButton moduleId={publicId} />
            </div>
        </div>
    )
}

export default ModuleBuilderHeader