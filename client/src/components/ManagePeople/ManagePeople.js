import './ManagePeople.css';
import { useState } from 'react';
import AddUserLayout from './AddUserLayout';
import OrgMemberList from './OrgMemberList';
import PendingInvites from './PendingInvites';
import SuperAdminsList from './SuperAdminsList';
import ManagePeopleHeader from './ManagePeopleHeader';
import { Navigate, Route, Routes } from 'react-router';

const ManagePeople = () => {

    const [searchQuery, setSearchQuery] = useState('');
    const [showAddUserLayout, setShowAddUserLayout] = useState(false);

    return (
        <div className="manage-people-container w100 h100">
            {showAddUserLayout && <AddUserLayout setShowAddUserLayout={setShowAddUserLayout} />}
            <ManagePeopleHeader setShowAddUserLayout={setShowAddUserLayout} searchQuery={searchQuery} setSearchQuery={setSearchQuery} />

            <Routes>
                <Route path="/all-members" element={<OrgMemberList searchQuery={searchQuery} />} />
                <Route path="/super-admins" element={<SuperAdminsList searchQuery={searchQuery} />} />
                <Route path="/pending-invites" element={<PendingInvites searchQuery={searchQuery} />} />
                <Route path="/all-members" element={<OrgMemberList searchQuery={searchQuery} />} />
                <Route path='*' element={<Navigate to="/settings/manage-people/all-members" />} />
            </Routes>
        </div>
    );
};

export default ManagePeople;
