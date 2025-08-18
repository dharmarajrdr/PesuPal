import { useEffect } from 'react'
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { apiRequest } from '../../http_request';
import { showPopup } from '../../store/reducers/PopupSlice';

const AuthModal = ({ setIsSubscriptionExpired, setAuthenticated }) => {

    const navigate = useNavigate();
    const dispatch = useDispatch();

    const token = sessionStorage.getItem('token');

    // 1. If no token, redirect to /signin
    if (!token) {
        navigate('/signin');
    }

    useEffect(() => {

        // 2. If the token is present, check `who am I?` endpoint
        apiRequest(`/api/v1/people/who-am-i`, 'GET').then(({ data }) => {
            const { orgMemberId, userId, orgStatus } = data || {};
            if (userId == null) {
                throw new Error('Session expired');
            } else if (orgStatus === 'Inactive') {
                // navigate('/settings/pricing');
                setIsSubscriptionExpired(true);
            } else if (orgMemberId == null) {
                navigate('/');
            }
        }).catch(({ message }) => {
            // sessionStorage.removeItem('token');
            // navigate('/signin');
            dispatch(showPopup({ message, type: 'error' }));
        }).finally(() => {
            setAuthenticated(false);
        })
    }, []);

    return null;
}

export default AuthModal