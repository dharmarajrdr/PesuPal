import './TeamMainContainer.css';
import PageNotFound from '../Auth/PageNotFound';
import RecruitLayout from '../Recruit/RecruitLayout';
import { Navigate, Route, Routes } from 'react-router-dom';
import DepartmentLayout from '../Department/DepartmentLayout';
import AllDepartmentsTreeView from '../Department/AllDepartmentsTreeView';

const TeamMainContainer = ({ width }) => {
    return (
        <div id='TeamMainContainer' style={{ width }}>
            <Routes>
                <Route path="" element={<Navigate to="/team/dashboard" />} />
                <Route path="/dashboard/*" element={<DepartmentLayout />} />
                <Route path="/recruit/*" element={<RecruitLayout />} />
                <Route path='/departments' element={<AllDepartmentsTreeView />} />
                <Route path="*" element={<PageNotFound />} />
            </Routes>
        </div>
    )
}

export default TeamMainContainer