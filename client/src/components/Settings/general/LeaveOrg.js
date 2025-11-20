import './DeleteOrg.css';

import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router';
import { apiRequest } from '../../../http_request';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';


const LeaveOrg = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();

    const leaveOrgHandler = () => {
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to leave this organisation?',
            options: [
                {
                    title: 'Leave',
                    color: '#ff9800',
                    onClick: () => {
                        apiRequest(`/api/v1/org/leave`, 'PATCH').then(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            dispatch(showPopup({ message, type: 'success' }));
                            navigate('/');
                        }).catch(({ message }) => {
                            dispatch(showPopup({ message, type: 'error' }));
                        });
                    }
                },
                {
                    title: 'Cancel',
                    color: 'gray',
                    onClick: () => dispatch(hideConfirmationPopup())
                }
            ]
        }));
    }

    return (
        <div id='leave-org' className='w100'>
            <h3 id='title'>Leave Organization</h3>
            <p id='description' className='mT10'>Are you sure you want to leave this organization? Once you leave, you will lose access to all the data and resources associated with it.</p>
            <div className='FCSS mB10'>
                <span className='warnings'>1. If you are the owner of the organization, you must transfer ownership before leaving.</span>
                <span className='warnings'>2. You will lose access to all the data and resources associated with this organization.</span>
                <span className='warnings'>3. You can rejoin the organization only if you are invited again by an existing member.</span>
            </div>
            <button id='leave-org-button' className='mT5' onClick={leaveOrgHandler}>
                <i className='fa fa-sign-out w15 mR5' aria-hidden="true"></i>
                Leave Organization
            </button>
        </div>
    )
}

export default LeaveOrg