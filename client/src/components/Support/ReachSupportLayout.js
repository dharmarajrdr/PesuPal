import { Route, Routes } from 'react-router-dom';
import './ReachSupportLayout.css';
import SupportTicketList from './SupportTicketList';
import TicketDetailView from './TicketDetailView';
import { useState } from 'react';
import CreateTicketLayout from './CreateTicketLayout';

const TicketPlaceholder = () => {

    return <div id='ticket-placeholder' className='FCCC h100'>
        <div id='ticket-placeholder-image' >
            <img src='https://png.pngtree.com/png-clipart/20230825/original/pngtree-technical-support-man-contacting-customer-picture-image_8725447.png' />
        </div>
        <div className='FCCC'>
            <p className='fs14'>Select a ticket to view details</p>
        </div>
    </div>
}

const ticketColor = {
    'Open': 'red',
    'In Progress': 'blue',
    'Pending Review': 'orange',
    'Closed': 'green'
}

const ReachSupportLayout = () => {

    const [selectedTicket, setSelectedTicket] = useState(null);
    const [showCreateTicket, setShowCreateTicket] = useState(false);

    return (
        <div id='reach-support-layout' className='layout FRSS'>
            <div className='FCSS' id='left-panel'>
                <div id='support-tickets-header' className='w100 FRCB'>
                    <p>My Tickets</p>
                    <div>
                        <i className='cursP w20 fa fa-filter' title='Filter Tickets'></i>
                        {showCreateTicket && <CreateTicketLayout onCancel={() => setShowCreateTicket(false)} />}
                        <i className='cursP mL15 w20 fa fa-plus' title='Create New Ticket' onClick={() => setShowCreateTicket(true)}></i>
                    </div>
                </div>
                <SupportTicketList setSelectedTicket={setSelectedTicket} ticketColor={ticketColor} />
            </div>
            <div id='right-panel' className='w100 h100'>
                <Routes>
                    <Route path='/' element={<TicketPlaceholder />} />
                    <Route path='/:ticketId' element={<TicketDetailView ticket={selectedTicket} ticketColor={ticketColor} />} />
                </Routes>
            </div>
        </div>
    )
}

export default ReachSupportLayout