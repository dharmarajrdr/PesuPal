import React from 'react'
import './SearchUsers.css'

const SearchUsers = () => {
    return (
        <div className='FRCB w100' id='SearchUserQuickAction'>
            <div className='searchBox'>
                <input type='text' placeholder='Search Users' className='w100' autoComplete='off' spellCheck='false' />
                <i className='fas fa-search'></i>
            </div>
            <i className='fas fa-plus quickActions cursP'></i>
        </div>
    )
}

export default SearchUsers