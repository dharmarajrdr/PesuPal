import { useState } from 'react';
import utils from '../../utils';
import { useNavigate } from 'react-router-dom'
import Loader from '../Loader';

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

    const navigate = useNavigate();
    const { ticketId, subject, description, status, createdAt } = ticket;
    const route = `/more/reach-support/${ticketId}`;

    const viewTicketHandler = () => {
        setSelectedTicket(ticket);
        navigate(route);
    }

    return (
        <div className='support-ticket-preview w100' onClick={viewTicketHandler}>
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

    const [tickets, setTickets] = useState([
        {
            ticketId: "TCK-1001",
            subject: "Login page error when entering special characters Login page error when entering special characters",
            description: "When a user attempts to log in using special characters in the username field, the system throws a server-side validation error instead of a client-side alert. This happens only on the production environment and not on staging. Steps to reproduce: 1) Go to login page 2) Enter username containing symbols like # or $ 3) Press Enter. Expected: client-side warning. Actual: server error. This issue was first reported after the last deployment.",
            status: "Open",
            createdAt: "2025-08-07T09:15:32Z"
        },
        {
            ticketId: "TCK-1002",
            subject: "Mobile UI alignment issue in dashboard widgets",
            description: "On mobile view (iPhone 12, Safari), dashboard widgets overlap with each other, making the data unreadable. This happens particularly when there are more than five widgets added by the user. The problem appears to be related to CSS flexbox rules and insufficient min-height on widget containers.",
            status: "In Progress",
            createdAt: "2025-06-06T11:42:19Z"
        },
        {
            ticketId: "TCK-1003",
            subject: "Email notifications not sent for password reset requests",
            description: "Several users have reported that they are not receiving password reset emails. The mail logs show entries for password reset attempts, but no outbound SMTP activity. This issue is intermittent and may be linked to the recent upgrade of the mail server's SSL certificate. Logs from August 1st show multiple 'Connection Timeout' errors when attempting to connect to smtp.mailserver.com on port 465.",
            status: "Pending Review",
            createdAt: "2024-08-06T15:05:48Z"
        }
    ]);

    const [loader, setLoader] = useState(false);

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