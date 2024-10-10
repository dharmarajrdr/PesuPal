import React from 'react'
import FileManager from './FileManager'
import FileCategoryShortcut from './FileCategoryShortcut'
import PreviewFolderFileList from './PreviewFolderFileList'
import './DriveDashboard.css'
import DriveUsageStats from './DriveUsageStats'

const DriveDashboard = () => {
    return (
        <div className='FCSS p20 w100'>
            <FileManager />
            <FileCategoryShortcut />
            <div className='FRSB w100'>
                <div>
                    <PreviewFolderFileList />
                    <PreviewFolderFileList />
                </div>
                <div>
                    <DriveUsageStats />
                </div>
            </div>
        </div>
    )
}

export default DriveDashboard