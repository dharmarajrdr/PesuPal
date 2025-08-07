import React from 'react'
import DriveDashboard from './DriveDashboard'
import FileManager from './FileManager'
import { Navigate, Route, Routes } from 'react-router-dom'

const DriveLayout = () => {
    return <div className='FCSS p20 w100'>
        <FileManager />
        <Routes>
            <Route path='' element={<Navigate to="/team/dashboard" />} />
            <Route path='/home/*' element={<DriveDashboard />} />
        </Routes>
    </div>
}

export default DriveLayout