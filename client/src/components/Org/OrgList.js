import { useEffect, useState } from 'react'
import OrgPreview from './OrgPreview'
import './OrgList.css'
import ErrorMessage from '../ErrorMessage';
import { apiRequest } from '../../http_request';
import Loader from '../Loader';
import { useNavigate } from "react-router-dom";

const OrgList = ({ toggleOrgList, closeOrgList }) => {

    const [orgListDetails, setOrgListDetails] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    const setCurrentOrg = (org) => {

        sessionStorage.setItem("org-id", org.id);
        if (window.location.pathname === "/feeds") {
            window.location.reload(); // Already in /feeds → just reload
        } else {
            navigate("/feeds"); // Else, navigate
        }
        sessionStorage.setItem("popup-message", `Switched to org '${org.displayName}'.`);
        sessionStorage.setItem("popup-type", "success");
        closeOrgList(); // Close the org list modal after setting the current org
    };

    const currentOrgId = sessionStorage.getItem('org-id');

    useEffect(() => {
        apiRequest('/api/v1/people/orgs', 'GET').then(({ data }) => {
            setLoading(false);
            setOrgListDetails(data);
        }).catch((error) => {
            setLoading(false);
            setError(error.message);
        });
    }, []);

    return (
        <div className='FCCS w100' id='org-list-modal' onClick={closeOrgList}>
            <div className='org-list-popup FCCS'>
                {
                    loading ? <Loader /> :
                        error ? <ErrorMessage message={error} /> :
                            orgListDetails.length ?
                                orgListDetails.map((org, index) => <OrgPreview org={org} currentOrgId={currentOrgId} setCurrentOrg={setCurrentOrg} key={index} toggleOrgList={toggleOrgList} />) :
                                <p className='no-orgs'>No organizations found</p>
                }
            </div>
        </div>
    )
}

export default OrgList