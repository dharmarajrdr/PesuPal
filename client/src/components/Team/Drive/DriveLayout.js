import PageNotFound from '../../Auth/PageNotFound'
import DriveDashboard from './DriveDashboard'
import FileCategoryShortcut from './FileCategoryShortcut'
import FileManager from './FileManager'
import { Navigate, Route, Routes } from 'react-router-dom'

const DriveLayout = () => {

    return <div className='FCSS p20 w100 Layout'>
        <FileManager />
        <FileCategoryShortcut />
        <Routes>
            <Route path='/' element={<Navigate to='/store/personal_space' />} />
            <Route path='/trash' element={<PageNotFound />} />
            <Route path='/:space' element={<DriveDashboard />} />
            <Route path='/:space/folder' element={<Navigate to='/store/personal_space' />} />
            <Route path='/:space/folder/:folderId' element={<DriveDashboard />} />
        </Routes>
    </div>
}

export default DriveLayout