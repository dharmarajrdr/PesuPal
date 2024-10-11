import React from 'react'
import FileManager from './FileManager'
import FileCategoryShortcut from './FileCategoryShortcut'
import PreviewFolderFileList from './PreviewFolderFileList'
import './DriveDashboard.css'
import DriveDashboardList from './DriveDashboardList';
import DriveUsageStats from './DriveUsageStats'
import RecentlyAccessedFiles from './RecentlyAccessedFiles'

const DriveDashboard = () => {
    return (
        <div className='FCSS p20 w100'>
            <FileManager />
            <FileCategoryShortcut />
            <div className='FRSB pY20 w100' id='previews_stats'>
                <div id='list_of_previews'>
                    {DriveDashboardList.map((item, index) => <PreviewFolderFileList key={index} item={item} />)}
                    <RecentlyAccessedFiles />
                </div>
                <DriveUsageStats />
            </div>
        </div>
    )
}

export default DriveDashboard