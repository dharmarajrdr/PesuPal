import { useState } from 'react';
import './FileManagerItem.css';
import { NavLink } from 'react-router-dom';
import FileManagerList from './FileManagerList'
import NewFolderLayout from './NewFolderLayout';

const FileManagerItem = ({ item }) => {
    const { id, title, route, icon, color } = item;
    return (
        <NavLink
            className={({ isActive }) => (isActive ? 'FileManagerItemActive ' : '') + 'FRCC FileManagerItem mR10'} to={route}>
            {({ isActive }) => (
                <>
                    <i className={icon} style={isActive ? {} : { color }} ></i>
                    <span>{title}</span>
                </>
            )}
        </NavLink>
    )

}

const FileManager = () => {

    const [showCreateFolderModal, setShowCreateFolderModal] = useState(false);

    return (
        <div className='FRCB w100 mb20' id='FileManager'>
            <div className='FRCC'>
                {FileManagerList.map((item, index) => <FileManagerItem key={index} item={item} />)}
            </div>
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
        </div>
    )
}

export default FileManager