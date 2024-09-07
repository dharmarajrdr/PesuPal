import React from 'react'
import { NavLink } from 'react-router-dom';
import './LeftNavigation.css'
import themes from '../../theme';

const Nav = ({ icon, title, route }) => {
    const { activeNav } = themes,
        isActive = document.location.pathname == route;
    return (
        <NavLink to={route} style={isActive ? activeNav : null} className={`LeftNavigationItem cursP FCCC selectNone ${isActive ? 'activeNav' : ''}`}  >
            <i className={`fas ${icon} colorFFF`}></i>
            <span className='colorFFF'>{title}</span>
        </NavLink>
    )
}

export default Nav;