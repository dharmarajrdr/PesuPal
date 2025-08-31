import './ManagePeople.css';
import Loader from '../Loader';
import { useDispatch } from "react-redux";
import AddUserLayout from './AddUserLayout';
import OrgMemberList from './OrgMemberList';
import { apiRequest } from '../../http_request';
import { useEffect, useRef, useState } from 'react';
import ManagePeopleHeader from './ManagePeopleHeader';
import { showPopup } from '../../store/reducers/PopupSlice';
import { hideLoader, showLoader } from '../../store/reducers/VerticalLoaderSlice';

const ManagePeople = () => {

    const sortOrder = 'ASC';
    const sortBy = 'employeeId';
    const dispatch = useDispatch();
    const firstRender = useRef(true);
    const [members, setMembers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [searchQuery, setSearchQuery] = useState('');
    const [showAddUserLayout, setShowAddUserLayout] = useState(false);

    const getUsers = () => {
        apiRequest(`/api/v1/people/search?query=${searchQuery}&sortBy=${sortBy}&sortOrder=${sortOrder}`, 'GET').then(({ data }) => {
            setMembers(data);
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        }).finally(() => {
            setLoading(false);
            dispatch(hideLoader());
        });
    }

    useEffect(() => {
        setLoading(true);
        getUsers();
    }, []);

    useEffect(() => {

        if (firstRender.current) {
            firstRender.current = false;
            return;
        }

        const delayDebounceFn = setTimeout(() => {
            dispatch(showLoader());
            getUsers();
        }, 1000);

        return () => clearTimeout(delayDebounceFn);
    }, [searchQuery]);

    return (
        <div className="manage-people-container w100 h100">
            <ManagePeopleHeader setShowAddUserLayout={setShowAddUserLayout} searchQuery={searchQuery} setSearchQuery={setSearchQuery} />
            {loading ? <Loader /> : <OrgMemberList members={members} />}
            {showAddUserLayout && <AddUserLayout setShowAddUserLayout={setShowAddUserLayout} />}
        </div>
    );
};

export default ManagePeople;
