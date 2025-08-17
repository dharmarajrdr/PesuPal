import { useState } from 'react';
import NewFolderLayout from './NewFolderLayout';

const HeaderActions = () => {

    const [showCreateFolderModal, setShowCreateFolderModal] = useState(false);
    return (
        <div className='FRCE'>
            {showCreateFolderModal && <NewFolderLayout onClose={() => { setShowCreateFolderModal(false); }} />}
            <button className='FRCC mR10' id='newFolderButton' onClick={() => setShowCreateFolderModal(true)}>
                <i className='fa fa-plus pR5 w_20'></i>
                <span>New Folder</span>
            </button>
            <button className='FRCC' id='uploadButton'>
                <i className='fa fa-upload pR5 w_20'></i>
                <span>Upload Files</span>
            </button>
        </div>
    )
}

export default HeaderActions