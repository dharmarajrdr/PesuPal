import FileCategoryShortcut from './FileCategoryShortcut'
import PreviewFolderFileList from './PreviewFolderFileList'
import './DriveDashboard.css'
import DriveDashboardList from './DriveDashboardList';
import RecentlyAccessedFiles from './RecentlyAccessedFiles'

const DriveDashboard = () => {
    return (
        <div className='FCSS w100' id='DriveDashboard'>

            <FileCategoryShortcut />
            <div className='FRSB w100' id='previews_stats'>
                <div id='list_of_previews'>
                    {DriveDashboardList.map((item, index) => <PreviewFolderFileList key={index} item={item} />)}
                    <RecentlyAccessedFiles />
                </div>
            </div>
        </div>
    )
}

export default DriveDashboard