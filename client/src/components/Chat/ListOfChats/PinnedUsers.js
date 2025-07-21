import './PinnedUsers.css'
import { useEffect, useState } from 'react';
import PinnedUser from './PinnedUser';
import { apiRequest } from '../../../http_request';
import ErrorMessage from '../../ErrorMessage';
import { useDispatch, useSelector } from 'react-redux';
import { setPinnedDirectMessages } from '../../../store/reducers/PinnedDirectMessageSlice';

const PinnedUsers = () => {

    const [error, setError] = useState(null);
    const pinnedUsers = useSelector(state => state.pinnedDirectMessage.pinnedDirectMessages);
    const dispatch = useDispatch();

    useEffect(() => {
        apiRequest("/api/v1/pinned-direct-messages", "GET").then(({ data }) => {
            dispatch(setPinnedDirectMessages(data));
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
