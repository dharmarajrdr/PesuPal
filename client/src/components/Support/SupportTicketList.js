import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from "react-redux";
import utils from '../../utils';
import { useNavigate, useParams } from 'react-router-dom'
import Loader from '../Loader';
import { apiRequest } from '../../http_request';
import { showPopup } from '../../store/reducers/PopupSlice';
import { setTickets } from '../../store/reducers/SupportTicketsSlice';

const NoTicketsFound = () => {
    return (
        <div className='FCCC w100 h100P' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-smile mR5'></i>
                No tickets available
            </p>
        </div>
    )
}

const SupportTicketPreview = ({ ticket, setSelectedTicket, ticketColor }) => {

    const params = useParams();
    const currentTicketId = params['*'];
    const navigate = useNavigate();
    const { ticketId, subject, description, status, createdAt } = ticket;
    const route = `/more/reach-support/${ticketId}`;

    const viewTicketHandler = () => {
        setSelectedTicket(ticket);
        navigate(route);
    }

    return (
        <div className={`support-ticket-preview w100 ${currentTicketId === ticketId ? 'active' : ''}`} onClick={viewTicketHandler}>
            <h5 className='ticket-subject'>
                <i className='fa fa-ticket pR10 w20' title={status} style={{ color: ticketColor?.[status] }}></i>
                {subject}
            </h5>
            <p className='ticket-description'>{description}</p>
            <div className='ticket-meta FRSS w100 mT5'>
                <span className='ticket-created-at pR10 mR10' title={utils.convertDateAndTime(createdAt)}>{utils.agoTimeCalculator(createdAt)}</span>
            </div>
        </div>
    )
}

const SupportTicketList = ({ setSelectedTicket, ticketColor }) => {

    const { tickets } = useSelector(state => state.supportTickets);
    const [loader, setLoader] = useState(true);
    const dispatch = useDispatch();

    useEffect(() => {
        apiRequest(`/api/v1/support/tickets`, 'GET').then(({ data }) => {
            dispatch(setTickets(data));
            setLoader(false);
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
            setLoader(false);
        });
    }, []);

    return (
        <div id='support-ticket-list' className='w100'>
            {loader ? <Loader /> :
                tickets.length ?
                    tickets.map(ticket => <>
                        <SupportTicketPreview key={ticket.id} ticket={ticket} setSelectedTicket={setSelectedTicket} ticketColor={ticketColor} />
                    </>) : <NoTicketsFound />}
        </div>
    )
}

export default SupportTicketList