import { useEffect } from 'react'
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { apiRequest } from '../../http_request';
import { setCurrentOrgId } from '../../store/reducers/OrgSlice';

const AuthModal = ({ setIsSubscriptionExpired, setAuthenticated, setServerDown }) => {

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
            const { orgMemberId, userId, orgStatus, orgId } = data || {};
            if (userId == null) {
                throw new Error('Session expired');
            } else if (orgStatus === 'Inactive') {
                // navigate('/settings/pricing');
                setIsSubscriptionExpired(true);
            } else if (orgMemberId == null) {
                navigate('/');
            } else {
                dispatch(setCurrentOrgId(orgId));
            }
            setAuthenticated(true);
        }).catch((data) => {
            setAuthenticated(true);
            const { statusCode } = data || {};
            if (!statusCode) {
                return setServerDown(true);
            }
            sessionStorage.removeItem('token');
            navigate('/signin');
        });
    }, []);

    return null;
}

export default AuthModal