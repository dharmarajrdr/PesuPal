import { useState } from 'react'
import './CreateTicketLayout.css'
import AddAttachments from '../AddAttachments'
import { useDispatch } from 'react-redux';
import { showPopup } from '../../store/reducers/PopupSlice';

const CreateTicketLayout = ({ onCancel }) => {

    const [title, setTitle] = useState('Some random title for the ticket');
    const [description, setDescription] = useState('Some random description for the ticket that is at least 30 characters long. This is just a placeholder text to demonstrate the layout and functionality of the ticket creation form. It should be descriptive enough to give an idea of the issue or request being made.');
    const [files, setFiles] = useState([]);
    const dispatch = useDispatch();

    const submitTicketHandler = (e) => {
        e.preventDefault();
        if (!title || !title.trim().length) {
            return dispatch(showPopup({ message: 'Title must be at least 10 characters long.', type: 'error' }));
        }
        if (!description || !description.trim().length) {
            return dispatch(showPopup({ message: 'Description must be at least 30 characters long.', type: 'error' }));
        }

        console.log('Ticket Created:', {
            title,
            description,
            attachments: files.map(file => file.file.name)
        });

    }

    return (
        <div id='create-ticket-layout' className='entire-screen-overlay'>
            <div id='create-ticket-container' className='centerMe w100 FCSS'>
                <div id='create-ticket-header' className='FCSS w100'>
                    <h2 className='w100'>Create a New Ticket</h2>
                </div>
                <div id='create-ticket-form' className='FCSS w100'>
                    <div className='row FRSS w100'>
                        <label>Title</label>
                        <input type='text' value={title} placeholder='Enter a title (required)' minLength={10} maxLength={100} onChange={(e) => setTitle(e.target.value)} />
                    </div>
                    <div className='row FRSS w100'>
                        <label>Description</label>
                        <textarea value={description} placeholder='Enter a detailed description (required)' minLength={30} maxLength={999} onChange={(e) => setDescription(e.target.value)} />
                    </div>
                    <div className='row FRSS w100 mT10'>
                        <AddAttachments allowedTypes={["image/png"]} maxFileSize={2 * 1024 * 1024} maxFiles={5} files={files} setFiles={setFiles} />
                    </div>
                    <div className='row FRCE w100'>
                        <button id='cancel-button' className='mR10' onClick={onCancel}>Cancel</button>
                        <button id='create-button' onClick={submitTicketHandler}>Create</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default CreateTicketLayout