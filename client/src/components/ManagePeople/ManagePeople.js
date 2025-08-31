import './ManagePeople.css';
import { useEffect, useState } from 'react';
import AddUserLayout from './AddUserLayout';
import OrgMemberList from './OrgMemberList';
import ManagePeopleHeader from './ManagePeopleHeader';
import { apiRequest } from '../../http_request';
import Loader from '../Loader';

const ManagePeople = () => {

    const [members, setMembers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [searchQuery, setSearchQuery] = useState('');
    const [showAddUserLayout, setShowAddUserLayout] = useState(false);

    useEffect(() => {
        setLoading(true);
        apiRequest(`/api/v1/people/search?query=${searchQuery}&sortBy=employeeId&sortOrder=ASC`, 'GET').then(({ data }) => {
            setMembers(data);
        }).catch(({ message }) => {
            console.error(message);
        }).finally(() => {
            setLoading(false);
        });
    }, []);

    return (
        <div className="manage-people-container w100 h100">
            <ManagePeopleHeader setShowAddUserLayout={setShowAddUserLayout} searchQuery={searchQuery} setSearchQuery={setSearchQuery} />
            {loading ? <Loader /> : <OrgMemberList members={members} />}
            {showAddUserLayout && <AddUserLayout setShowAddUserLayout={setShowAddUserLayout} />}
        </div>
    );
};

export default ManagePeople;
