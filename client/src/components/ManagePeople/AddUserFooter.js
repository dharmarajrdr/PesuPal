import React from 'react'
import './AddUserFooter.css';

const AddUserFooter = () => {
    return (
        <div id='add-user-footer' className='FRSC w100'>
            <div id='left' className='FRCS'>
                <button className='delete-user'>
                    <i className='fa fa-trash pR5' aria-hidden='true'></i>
                    Delete User
                </button>
            </div>
            <div id='right' className='FRCE'>
                <button className='mR10' id='cancel-button'>Cancel</button>
                <button id='add-user-button'>Add User</button>
            </div>
        </div>
    )
}

export default AddUserFooter