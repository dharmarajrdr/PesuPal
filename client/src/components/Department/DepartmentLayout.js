import { useState } from 'react';
import DepartmentHeader from './DepartmentHeader';
import './DepartmentLayout.css';
import DepartmentMain from './DepartmentMain';
import { apiRequest } from '../../http_request';
import Loader from '../Loader';
import ErrorMessage from '../ErrorMessage';

const DepartmentLayout = () => {

    const [departmentId, setDepartmentId] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useState(() => {
        apiRequest("/api/v1/department", "GET").then(({ data }) => {
            setLoading(false);
            setDepartmentId(data.id);
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
                            <DepartmentHeader departmentId={departmentId} />
                            <DepartmentMain />
                        </>
            }
        </div>
    )
}

export default DepartmentLayout