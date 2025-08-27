import { useEffect, useState } from 'react'
import OrgPreview from './OrgPreview'
import './OrgList.css'
import { useDispatch } from "react-redux";
import ErrorMessage from '../ErrorMessage';
import { apiRequest } from '../../http_request';
import Loader from '../Loader';
import { useNavigate } from "react-router-dom";
import { showPopup } from '../../store/reducers/PopupSlice';

const NoOrgFound = () => {

    return (
        <div className='FCCC w100 h100P' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-building mR5' />
                Welcome to PesuPal!
            </p>
            <p className='w100 alignCenter'>Start creating a new org.</p>
        </div>
    )
}

const OrgList = ({ toggleOrgList, closeOrgList }) => {

    const [orgListDetails, setOrgListDetails] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const setCurrentOrg = ({ publicId, displayName }) => {

        apiRequest(`/api/v1/people/token`, 'GET', null, {
            'X-Org-Id': publicId
        }).then(({ data }) => {
            sessionStorage.setItem('token', data);
            if (window.location.pathname === "/feeds") {
                window.location.reload(); // Already in /feeds → just reload
            } else {
                navigate("/feeds"); // Else, navigate
            }
            dispatch(showPopup({ message: `Switched to '${displayName}' org.`, type: 'success' }));
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    };

    useEffect(() => {
        apiRequest('/api/v1/people/orgs', 'GET').then(({ data }) => {
            setLoading(false);
            setOrgListDetails(data);
        }).catch((error) => {
            setLoading(false);
            setError(error.message);
        });
    }, []);

    const createOrgHandler = () => {

        return navigate('/org/create');
    }

    return (
        <div className='FCCS w100' id='org-list-modal' onClick={closeOrgList}>
            <div className='org-list-popup FCCS'>
                {
                    loading ? <Loader /> :
                        error ? <ErrorMessage message={error} /> : <>
                            {orgListDetails.length ? <div id='org-lists' className='w100 h100P FCCS'>
                                {orgListDetails.map((org, index) => <OrgPreview org={org} setCurrentOrg={setCurrentOrg} key={index} toggleOrgList={toggleOrgList} />)}
                            </div> : <NoOrgFound />
                            }
                            <button className='create-org-button w100 FRCC' onClick={createOrgHandler}>Create New Org</button>
                        </>
                }
            </div>
        </div>
    )
}

export default OrgList