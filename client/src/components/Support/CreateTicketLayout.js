import { useState } from 'react'
import './CreateTicketLayout.css'
import AddAttachments from '../AddAttachments'

const CreateTicketLayout = ({ onCancel }) => {

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [attachments, setAttachments] = useState([]);
    const [error, setError] = useState('');

    const submitTicketHandler = (e) => {
        e.preventDefault();
        setError('');
        if (!title || !title.trim().length) {
            return setError('Title must be at least 10 characters long.');
        }
        if (!description || !description.trim().length) {
            return setError('Description must be at least 30 characters long.');
        }

    }

    return (
        <div id='create-ticket-layout' className='entire-screen-overlay'>
            <div id='create-ticket-container' className='centerMe w100 FCSS'>
                <div id='create-ticket-header' className='FCSS w100'>
                    <h2 className='w100'>Create a New Ticket</h2>
                </div>
                <form id='create-ticket-form' className='FCSS w100' onSubmit={submitTicketHandler}>
                    <div className='row FRSS w100'>
                        <label>Title</label>
                        <input type='text' placeholder='Enter a title (required)' minLength={10} maxLength={100} onChange={(e) => setTitle(e.target.value)} />
                    </div>
                    <div className='row FRSS w100'>
                        <label>Description</label>
                        <textarea placeholder='Enter a detailed description (required)' minLength={30} maxLength={999} onChange={(e) => setDescription(e.target.value)} />
                    </div>
                    <div className='row FRSS w100 mT10'>
                        <AddAttachments allowedTypes={["image/*", "video/*", "application/pdf"]} />
                    </div>
                    <div className='w100 mT10'>
                        <p className='error-message w100'>{error}</p>
                    </div>
                    <div className='row FRCE mT20 w100'>
                        <button id='cancel-button' className='mR10' onClick={onCancel}>Cancel</button>
                        <button id='create-button' type='submit'>Create</button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default CreateTicketLayout