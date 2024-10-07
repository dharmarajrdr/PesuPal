import React from 'react';
import './SearchPeople.css';

const SearchPeople = () => {
    return (
        <div id='search_people' className='w100 FRSS'>
            <div id='search_people_input_container' className='FRES'>
                <input type='input' placeholder='Search people, department,..' id='search_people_input' autoComplete='off' spellCheck='false' />
                <label className='FRCC color555' id='search_count'>
                    <b className='pR5 color555'>27</b> users found
                </label>
            </div>
        </div>
    )
}

export default SearchPeople