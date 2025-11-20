import { useEffect, useState } from 'react';
import DepartmentHeader from './DepartmentHeader';
import './DepartmentLayout.css';
import DepartmentMain from './DepartmentMain';
import { apiRequest } from '../../http_request';
import Loader from '../Loader';
import ErrorMessage from '../ErrorMessage';
import { useDispatch } from 'react-redux';
import { setCurrentDepartmentId, setCurrentDepartmentName } from '../../store/reducers/DepartmentSlice';

const DepartmentLayout = () => {

    const dispatch = useDispatch();
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        apiRequest("/api/v1/department", "GET").then(({ data }) => {
            setLoading(false);
            const { id, name } = data || {};
            dispatch(setCurrentDepartmentId(id));
            dispatch(setCurrentDepartmentName(name));
        }).catch(({ message }) => {
            setLoading(false);
            setError(message || "Failed to fetch department data.");
        });
    }, []);

    return (
        <div id='departmentLayout' className='w100 h100 FCSS'>
            {
                loading ? <Loader /> :
                    error ? <ErrorMessage message={error} /> :
                        <>
                            <DepartmentHeader />
                            <DepartmentMain />
                        </>
            }
        </div>
    )
}

export default DepartmentLayout