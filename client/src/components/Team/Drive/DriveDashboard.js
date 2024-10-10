import React from 'react'
import FileManager from './FileManager'
import FileCategoryShortcut from './FileCategoryShortcut'
import PreviewFolderFileList from './PreviewFolderFileList'

const DriveDashboard = () => {
    return (
        <div className='FCSS p20'>
            <FileManager />
            <FileCategoryShortcut />
            <PreviewFolderFileList />
            <PreviewFolderFileList />
        </div>
    )
}

export default DriveDashboard