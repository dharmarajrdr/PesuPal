import React from 'react'
import { BrowserRouter, Route, Routes, useNavigate } from 'react-router-dom';
import './TeamMainContainer.css';
import ManageWorkLayout from './ManageWork/ManageWorkLayout';
import DriveLayout from './Drive/DriveLayout';

const TeamMainContainer = () => {
    return (
        <div id='TeamMainContainer'>
            <Routes>
                <Route path="drive" element={<DriveLayout />} />
                <Route path="manage_work" element={<ManageWorkLayout />} />
            </Routes>
        </div>
    )
}

export default TeamMainContainer