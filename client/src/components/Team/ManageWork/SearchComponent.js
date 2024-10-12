import React, { useEffect, useState } from 'react'
import './SearchComponent.css'
import { useNavigate, useLocation } from 'react-router-dom';

const SearchComponent = () => {

    const navigate = useNavigate(),
        location = useLocation(),
        changeView = (e) => {
            const route = "/team/manage_work/" + e.target.value;
            navigate(route);
        }, [view, setView] = useState(null);

    useEffect(() => {
        const { pathname } = location;
        switch (true) {
            case pathname.includes('/list'):
                setView('list');
                break;
            case pathname.includes('/kanban'):
                setView('kanban');
                break;
            default:
                break;
        }
    }, []);

    return (
        <div id='SearchComponent' className='FRCE'>
            <select onChange={changeView} id='change_view' value={view}>
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