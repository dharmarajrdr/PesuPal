import './SuperAdminsList.css'
import Person from './Person'
import Loader from '../Loader'
import MemberStatus from './MemberStatus'
import { useDispatch } from 'react-redux'
import { apiRequest } from '../../http_request'
import { useEffect, useRef, useState } from 'react'
import { showPopup } from '../../store/reducers/PopupSlice'
import { hideLoader, showLoader } from '../../store/reducers/VerticalLoaderSlice'
import EmployeeId from './EmployeeId'
import Role from './Role'

const Actions = () => {

    return <div className='FRCE action-btns'>
        <span className='action-btn edit'><i className="fa fa-pencil w15" /> Edit</span>
    </div>
}

const Row = ({ person }) => {

    const { id, employeeId, archived } = person || {};

    return (
        <div className='row FRCS' key={id}>
            <div className='column'><EmployeeId employeeId={employeeId} /></div>
            <div className='column'><Person person={person} /></div>
            <div className='column'><MemberStatus archived={archived} /></div>
            <div className='column'><Role person={person} /></div>
            <div className='column'><Actions /></div>
        </div>
    )
}

const NoMembersFound = () => {

    return <div className='FCCC w100 h100P' id='no-data-found'>
        <p className='FRCC w100'>
            <i className='fa fa-user w15 mR5' />
            No members found
        </p>
        <p className='w100 alignCenter'>Start adding members to this org.</p>
    </div>
}

const SuperAdminsList = ({ searchQuery }) => {

    const sortOrder = 'ASC';
    const sortBy = 'employeeId';
    const dispatch = useDispatch();
    const firstRender = useRef(true);
    const [members, setMembers] = useState([]);
    const [loading, setLoading] = useState(true);

    const getUsers = () => {
        apiRequest(`/api/v1/people/super-admins?query=${searchQuery}&sortBy=${sortBy}&sortOrder=${sortOrder}`, 'GET').then(({ data }) => {
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

    return loading ? <Loader /> : (
        <div class='manage-people-body'>
            <div className='w100 manage-people-table' cellPadding={0} cellSpacing={0}>

                <div className='row header w100 FRCS'>
                    <div className='column'>ID</div>
                    <div className='column'>Person</div>
                    <div className='column'>Status</div>
                    <div className='column'>Role</div>
                    <div className='column'></div>
                </div>

                {members.length ? members.map((person, index) => <Row key={index} person={person} />) : <NoMembersFound />}

            </div>
        </div>
    )
}

export default SuperAdminsList;