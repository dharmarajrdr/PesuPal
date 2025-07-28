import React from 'react'
import './AddUserLayout.css'

const AddUserLayout = ({ setShowAddUserLayout }) => {
    const closeLayout = (e) => {
        e.stopPropagation();
        if (e.target.id !== 'add-user-layout') {
            setShowAddUserLayout(false);
        }
    };
    return (
        <div className='entire-screen-overlay' onClick={closeLayout}>
            <div id='add-user-layout' className='centerMe'>

            </div>
        </div>
    )
}

export default AddUserLayout