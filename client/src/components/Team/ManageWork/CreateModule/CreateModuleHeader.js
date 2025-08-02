import { useState } from 'react'
import OptionsModal from '../../../Utils/OptionsModal'
import './CreateModuleHeader.css'
import MyModulesListOverlay from './MyModulesListOverlay'

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

const CreateModuleHeader = () => {
    return (
        <div id='create-module-header' className='w100 FRCB'>
            <div className='FRCS'>

            </div>
            <div className='FRCE'>
                <PublishButton />
                <MoreOptionsButton />
            </div>
        </div>
    )
}

export default CreateModuleHeader