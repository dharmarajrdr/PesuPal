import './CreateModuleHeader.css'

const PublishButton = () => {
    return (
        <button id='publishButton'><i className='fa fa-paper-plane fs12 pR5 colorFFF'></i>Publish</button>
    )
}

const MoreOptionsButton = () => {

    return (
        <div id='more-options-button' className='FRCC mL10'>
            <i className='fa fa-ellipsis-vertical fs16'></i>
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