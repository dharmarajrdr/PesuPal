import React from 'react'
import './SearchComponent.css'

const SearchComponent = () => {
    return (
        <div id='SearchComponent'>
            <i className='fa fa-search'></i>
            <input type='text' placeholder='Search here...' autoComplete='off' spellCheck="false" />
        </div>
    )
}

export default SearchComponent