import React, { useEffect } from 'react'
import { NavLink } from 'react-router-dom';
import './LeftNavigation.css'
import themes from '../../theme';
import { useDispatch } from 'react-redux';
import { setActiveNavigation } from '../../store/actions/Navigation';

const Nav = ({ icon, image, title, route, isActive, notifyCount }) => {

    const { activeNav } = themes,
        dispatch = useDispatch(),
        handleNavClick = (event) => {
            // event.preventDefault();
            dispatch(setActiveNavigation({ route }));
        }

    return (
        <NavLink to={route} style={isActive ? activeNav : null} className={`LeftNavigationItem cursP FCCC selectNone ${isActive ? 'activeNav' : ''}`} onClick={handleNavClick}  >
            {
                icon ? <i className={`fas ${icon} colorFFF`}></i> : <img src={image} />
            }
            {notifyCount && notifyCount != '0' && <b className='notifyCount'>{notifyCount}</b>}
            <span className='colorFFF'>{title}</span>
        </NavLink>
    )
}

export default Nav;