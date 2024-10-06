import React from 'react';
import './SearchPeople.css';

const SearchPeople = () => {
    return (
        <div id='search_people' className='w100 FRSS'>
            <div id='search_people_input_container'>
                <input type='input' placeholder='Search people, department,..' id='search_people_input' autoComplete='off' spellCheck='false' />
            </div>
        </div>
    )
}

export default SearchPeople