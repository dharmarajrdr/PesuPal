import { Navigate, Route, Routes } from 'react-router-dom';
import './TeamMainContainer.css';
import RecruitLayout from '../Recruit/RecruitLayout';
import DepartmentLayout from '../Department/DepartmentLayout';
import PageNotFound from '../Auth/PageNotFound';

const TeamMainContainer = ({ width }) => {
    return (
        <div id='TeamMainContainer' style={{ width }}>
            <Routes>
                <Route path="" element={<Navigate to="/team/dashboard" />} />
                <Route path="/dashboard/*" element={<DepartmentLayout />} />
                <Route path="/recruit/*" element={<RecruitLayout />} />
                <Route path="*" element={<PageNotFound />} />
            </Routes>
        </div>
    )
}

export default TeamMainContainer