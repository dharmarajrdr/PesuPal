import React from 'react'
import { NavLink } from 'react-router-dom';
import './LeftNavigation.css'

const Nav = ({ icon, title, route }) => {
    return (
        <NavLink to={route} className={`LeftNavigationItem cursP FCCC ${document.location.pathname == route ? 'activeNav' : ''}`}>
            <i className={`fas ${icon} colorFFF`}></i>
            <span className='colorFFF'>{title}</span>
        </NavLink>
    )
}

export default Nav;