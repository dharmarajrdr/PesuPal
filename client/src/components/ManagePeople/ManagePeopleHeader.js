import React, { useState } from 'react'

const ManagePeopleHeader = () => {

    const [searchTerm, setSearchTerm] = useState('');

    return (
        <div className='FRCB w100' id='manage-people-header'>
            <input className="search-input" placeholder="Search people..." value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)} />
            <button className="add-btn"><i className="fa fa-user-plus colorFFF pR5" />Add New</button>
        </div>
    )
}

export default ManagePeopleHeader