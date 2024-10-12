import React from 'react'
import './SearchComponent.css'

const SearchComponent = ({ viewState }) => {

    const [, setView] = viewState,
        changeView = (e) => {
            setView(e.target.value);
        }

    return (
        <div id='SearchComponent' className='FRCE'>
            <select onChange={changeView} id='change_view'>
                <option value='list'>List</option>
                <option value='kanban'>Kanban</option>
            </select>
            <div id='searchDiv'>
                <i className='fa fa-search'></i>
                <input type='text' placeholder='Search here...' autoComplete='off' spellCheck="false" />
            </div>
        </div>

    )
}

export default SearchComponent