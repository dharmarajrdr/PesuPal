import { useEffect, useState } from 'react';
import './DepartmentMain.css';
import OrgMembers from './OrgMembers';
import { apiRequest } from '../../http_request';
import Loader from '../Loader';
import ErrorMessage from '../ErrorMessage';
import { useDispatch, useSelector } from 'react-redux';
import { setCurrentDepartmentMembers } from '../../store/reducers/DepartmentSlice';

const NoUsersFound = () => {

    return (
        <div className='FCCC w100 h100' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-users mR5' />
                No members found in this department.
            </p>
        </div>
    )
}

const DepartmentMain = () => {

    const dispatch = useDispatch();
    const department = useSelector(state => state.department) || {};
    const departmentId = useSelector(state => state.department?.currentDepartment?.id);
    const members = useSelector(state => state.department?.currentDepartment?.members) || [];
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {

        if (!departmentId) return;

        setLoading(true);
        apiRequest(`/api/v1/department/${departmentId}/members`, "GET").then(({ data }) => {
            setLoading(false);
            dispatch(setCurrentDepartmentMembers(data));
        }).catch(({ message }) => {
            setLoading(false);
            setError(message);
        });

    }, [departmentId, dispatch]);


    const availableMembers = members.filter(member => member.status === 'AVAILABLE');
    const unavailableMembers = members.filter(member => member.status !== 'AVAILABLE');

    return loading ? <Loader /> :
        error ? <ErrorMessage message={error} /> :
            members.length ? (
                <div id="department-main" className='w100 h100 FRSB'>
                    <OrgMembers title="Available Members" orgMembersList={availableMembers} noMembersAvailableMessage="Currently, all of them are offline." />
                    <OrgMembers title="Offline Members" orgMembersList={unavailableMembers} noMembersAvailableMessage="Currently, all of them are online." />
                </div>
            ) : <NoUsersFound />
}

export default DepartmentMain