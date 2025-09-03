import './PendingInvites.css'
import Person from './Person'
import Loader from '../Loader'
import Inviter from './Inviter'
import { apiRequest } from '../../http_request'
import { useEffect, useRef, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { showPopup } from '../../store/reducers/PopupSlice'
import { hideLoader, showLoader } from '../../store/reducers/VerticalLoaderSlice'
import { hideConfirmationPopup, showConfirmationPopup } from '../../store/reducers/ConfirmationPopupSlice'
import { deleteUserByInvitationId, setManageUserTitle, setUsers } from '../../store/reducers/ManageUsersSlice'

const Actions = ({ invitationId }) => {

    const dispatch = useDispatch();
    const [isActive, setIsActive] = useState(false);

    const _finally = () => {
        setIsActive(false);
        dispatch(hideConfirmationPopup());
    }

    const resentInviteHandler = (e) => {
        e.stopPropagation();
        setIsActive(true);
        dispatch(showConfirmationPopup({
            message: 'Are you want to resend this invite?',
            options: [
                {
                    title: 'Resend',
                    color: '#097b8a',
                    onClick: () => {
                        apiRequest(`/api/v1/org-invitations/resend/${invitationId}`, 'PATCH').then(({ message }) => {
                            dispatch(showPopup({ message, type: 'success' }));
                            _finally();
                        }).catch(({ message }) => {
                            dispatch(showPopup({ message, type: 'error' }));
                            _finally();
                        });
                    }
                },
                {
                    title: 'Cancel',
                    color: 'gray',
                    onClick: () => {
                        _finally();
                    }
                }
            ]
        }));
    }

    const revokeInviteHandler = (e) => {
        e.stopPropagation();
        setIsActive(true);
        dispatch(showConfirmationPopup({
            message: 'Are you want to revoke this invite?',
            options: [
                {
                    title: 'Revoke',
                    color: '#d64545',
                    onClick: () => {
                        apiRequest(`/api/v1/org-invitations/revoke/${invitationId}`, 'DELETE').then(({ message }) => {
                            dispatch(showPopup({ message, type: 'success' }));
                            dispatch(deleteUserByInvitationId(invitationId));
                            _finally();
                        }).catch(({ message }) => {
                            dispatch(showPopup({ message, type: 'error' }));
                            _finally();
                        });
                    }
                },
                {
                    title: 'Cancel',
                    color: 'gray',
                    onClick: () => {
                        _finally();
                    }
                }
            ]
        }));
    }

    return <div className={`FRCE action-btns ${isActive ? 'active' : ''}`} onClick={() => setIsActive(!isActive)}>
        <span className='action-btn' onClick={resentInviteHandler}><i className='fa fa-paper-plane mR5' /> Resend Invite</span>
        <span className='action-btn' onClick={revokeInviteHandler}><i className='fa fa-trash mR5' /> Revoke Invite</span>
    </div>
}

const Row = ({ person }) => {

    const { invitationId, employeeId, archived, inviter, invitedAt } = person || {};

    return (
        <div className='row FRCS' key={invitationId}>
            <div className='column'><Person person={person} /></div>
            <div className='column'><Inviter inviter={inviter} invitedAt={invitedAt} /></div>
            <div className='column'><Actions invitationId={invitationId} /></div>
        </div>
    )
}

const NoMembersFound = () => {

    return <div className='FCCC w100 h100P' id='no-data-found'>
        <p className='FRCC w100'>
            <i className='fa fa-user w15 mR5' />
            No members found
        </p>
        <p className='w100 alignCenter'>Start adding members to this org.</p>
    </div>
}

const PendingInvites = ({ searchQuery }) => {

    const sortOrder = 'ASC';
    const sortBy = 'employeeId';
    const dispatch = useDispatch();
    const firstRender = useRef(true);
    const [loading, setLoading] = useState(true);
    const { members } = useSelector(state => state.manageUsers);

    const getUsers = () => {
        apiRequest(`/api/v1/org-invitations/pending-actions?query=${searchQuery}&sortBy=${sortBy}&sortOrder=${sortOrder}`, 'GET').then(({ data }) => {
            dispatch(setUsers(data));
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        }).finally(() => {
            setLoading(false);
            dispatch(hideLoader());
        });
    }

    useEffect(() => {
        dispatch(setManageUserTitle('Pending Invites'));
        setLoading(true);
        getUsers();
    }, []);

    useEffect(() => {

        if (firstRender.current) {
            firstRender.current = false;
            return;
        }

        const delayDebounceFn = setTimeout(() => {
            dispatch(showLoader());
            getUsers();
        }, 1000);

        return () => clearTimeout(delayDebounceFn);
    }, [searchQuery]);

    return loading ? <Loader /> : (
        <div className='manage-people-body'>
            <div className='w100 manage-people-table' id='pending-invites-table' cellPadding={0} cellSpacing={0}>

                <div className='row header w100 FRCS'>
                    <div className='column'>Person</div>
                    <div className='column'>Inviter</div>
                    <div className='column'></div>
                </div>

                {members.length ? members.map((person, index) => <Row key={index} person={person} />) : <NoMembersFound />}

            </div>
        </div>
    )
}

export default PendingInvites;