import React from 'react'
import { Navigate, Route, Routes } from 'react-router-dom';
import './TeamMainContainer.css';
import ManageWorkLayout from './ManageWork/ManageWorkLayout';
import DriveLayout from './Drive/DriveLayout';
import RecruitLayout from '../Recruit/RecruitLayout';
import DepartmentLayout from '../Department/DepartmentLayout';
import PageNotFound from '../Auth/PageNotFound';

const TeamMainContainer = ({ width }) => {
    return (
        <div id='TeamMainContainer' style={{ width }}>
            <Routes>
                <Route path="" element={<Navigate to="/team/drive" />} />
                <Route path="/dashboard/*" element={<DepartmentLayout />} />
                <Route path="/drive/*" element={<DriveLayout />} />
                <Route path="/recruit/*" element={<RecruitLayout />} />
                <Route path="*" element={<PageNotFound />} />
            </Routes>
        </div>
    )
}

export default TeamMainContainer