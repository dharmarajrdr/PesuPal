import React from 'react'
import { NavLink } from 'react-router-dom';
import './LeftNavigation.css'
import themes from '../../theme';

const Nav = ({ icon, image, title, route }) => {
    const { activeNav } = themes,
        isActive = document.location.pathname == route;
    return (
        <NavLink to={route} style={isActive ? activeNav : null} className={`LeftNavigationItem cursP FCCC selectNone ${isActive ? 'activeNav' : ''}`}  >
            {
                icon ? <i className={`fas ${icon} colorFFF`}></i> : <img src={image} />
            }
            <span className='colorFFF'>{title}</span>
        </NavLink>
    )
}

export default Nav;