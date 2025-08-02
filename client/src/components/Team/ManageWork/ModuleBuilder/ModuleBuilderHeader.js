import { useState } from 'react'
import OptionsModal from '../../../Utils/OptionsModal'
import { useSelector } from 'react-redux';
import './ModuleBuilderHeader.css'
import MyModulesListOverlay from '../CreateModule/MyModulesListOverlay'

const PublishButton = () => {
    return (
        <button id='publishButton'><i className='fa fa-paper-plane fs12 pR5 colorFFF'></i>Publish</button>
    )
}

const MoreOptionsButton = () => {

    const options = [
        {
            icon: 'fa fa-plus',
            name: 'New Module',
            onClick: () => console.log('New Module clicked')
        },
        {
            icon: 'fa fa-trash',
            name: 'Delete Module',
            onClick: () => console.log('Delete Module clicked')
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

const ModuleBuilderHeader = () => {

    const module = useSelector(state => state.currentModule.data);
    const { name, description } = module || {};

    return (
        <div id='create-module-header' className='w100 FRCB'>
            <div className='FRCS'>
                <i className='fa fa-arrow-left fs16 mR10' onClick={() => window.history.back()}></i>
                <h4 id='module-name'>{name}</h4>
                <i className='fa fa-info-circle fs12 mL10 colorDDD' title={description || 'No description found'}></i>
            </div>
            <div className='FRCE'>
                <PublishButton />
                <MoreOptionsButton />
            </div>
        </div>
    )
}

export default ModuleBuilderHeader