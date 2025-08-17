import { useState } from 'react'
import FileManager from './FileManager'
import InsideDirectory from './InsideDirectory'
import PageNotFound from '../../Auth/PageNotFound'
import FileCategoryShortcut from './FileCategoryShortcut'
import { Navigate, Route, Routes } from 'react-router-dom'
import PermissionDenied from '../../Auth/PermissionDenied'

const DriveLayout = () => {

    const [pageNotFound, setPageNotFound] = useState(false);
    const [permissionDenied, setPermissionDenied] = useState(false);

    return permissionDenied ? <PermissionDenied /> :
        pageNotFound ? <PageNotFound /> :
            <div className='FCSS p20 w100 Layout'>
                <FileManager />
                <FileCategoryShortcut />
                <Routes>
                    <Route path='/' element={<Navigate to='/store/personal_space' />} />
                    <Route path='/trash' element={<PageNotFound />} />
                    <Route path='/:space' element={<InsideDirectory setPageNotFound={setPageNotFound} setPermissionDenied={setPermissionDenied} />} />
                    <Route path='/:space/folder' element={<Navigate to='/store/personal_space' />} />
                    <Route path='/:space/folder/:folderId' element={<InsideDirectory setPageNotFound={setPageNotFound} setPermissionDenied={setPermissionDenied} />} />
                </Routes>
            </div>
}

export default DriveLayout