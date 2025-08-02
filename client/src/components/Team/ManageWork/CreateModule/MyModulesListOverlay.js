import './MyModulesList.css';

const MyModulesList = () => {

    return (
        <div id='my-modules-list' className='FCSS h100'>
            <h2>Created By Me</h2>

        </div>
    )
}

const MyModulesListOverlay = ({ onCloseModal }) => {
    return (
        <div id='my-modules-list-overlay' className='entire-screen-overlay FRSE' onClick={onCloseModal}>
            <MyModulesList />
        </div>
    )
}

export default MyModulesListOverlay