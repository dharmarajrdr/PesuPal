import React from 'react'
import { Navigate, Route, Routes } from 'react-router-dom';
import './TeamMainContainer.css';
import ManageWorkLayout from './ManageWork/ManageWorkLayout';
import DriveLayout from './Drive/DriveLayout';

const TeamMainContainer = ({ width }) => {
    return (
        <div id='TeamMainContainer' style={{ width }}>
            <Routes>
                <Route path="" element={<Navigate to="/team/drive" />} />
                <Route path="/drive/*" element={<DriveLayout />} />
                <Route path="/manage_work/*" element={<ManageWorkLayout />} />
            </Routes>
        </div>
    )
}

export default TeamMainContainer