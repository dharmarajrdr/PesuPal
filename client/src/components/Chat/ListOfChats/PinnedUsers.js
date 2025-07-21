import './PinnedUsers.css'
import { useEffect, useState } from 'react';
import PinnedUser from './PinnedUser';
import { apiRequest } from '../../../http_request';
import ErrorMessage from '../../ErrorMessage';

const NoPinnedUsers = () => {
    return (
        <div className='FCCC w100 h100' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-briefcase mR5' />
                No job applications found.
            </p>
            <p className='w100 alignCenter'>Start creating a new job opening.</p>
        </div>
    )
}

const PinnedUsers = () => {

    const [pinnedUsers, setPinnedUsers] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        apiRequest("/api/v1/pinned-direct-messages", "GET").then(({ data }) => {
            setPinnedUsers(data);
        }).catch(({ message }) => {
            setError(message);
        });
    }, []);

    return (
        <div id='PinnedUsers' className='noScrollbar selectNone'>
            <div id='PinnedUsersFrame'>
                {error ? <ErrorMessage message={error} /> :
                    pinnedUsers.map((item, index) => (
                        <PinnedUser item={item} key={index} />
                    ))
                }
            </div>
        </div>
    );
};

export default PinnedUsers;
