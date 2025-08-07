import { useEffect, useState } from 'react';
import './DepartmentMain.css';
import OrgMembers from './OrgMembers';
import { apiRequest } from '../../http_request';
import Loader from '../Loader';
import ErrorMessage from '../ErrorMessage';

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

const DepartmentMain = ({ departmentId, currentDepartment }) => {

  const [orgMembersList, setOrgMembersList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    apiRequest(`/api/v1/department/${currentDepartment?.id || departmentId}/members`, "GET").then(({ data }) => {
      setLoading(false);
      setOrgMembersList(data);
    }).catch(({ message }) => {
      setLoading(false);
      setError(message);
    });
  }, [currentDepartment]);

  const availableMembers = orgMembersList.filter(member => member.status === 'available');
  const unavailableMembers = orgMembersList.filter(member => member.status !== 'available');

  return loading ? <Loader /> :
    error ? <ErrorMessage message={error} /> :
      orgMembersList.length ? (
        <div id="department-main" className='w100 h100 FRSB'>
          <OrgMembers title="Available Members" orgMembersList={availableMembers} noMembersAvailableMessage="Currently, all of them are offline." />
          <OrgMembers title="Unavailable Members" orgMembersList={unavailableMembers} noMembersAvailableMessage="Currently, all of them are online." />
        </div>
      ) : <NoUsersFound />
}

export default DepartmentMain