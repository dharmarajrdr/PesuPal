import React from 'react'
import { NavLink } from 'react-router-dom';
import './LeftNavigation.css'

const Nav = ({ icon, image, title, route, notifyCount }) => {

    return route ? (
        <NavLink to={route} className={({ isActive }) => (isActive ? 'activeNav' : '') + ` LeftNavigationItem cursP FCCC selectNone`}  >
            {
                icon ? <i className={`fas ${icon} colorFFF`}></i> : <img src={image} />
            }
            {notifyCount && notifyCount != '0' && <b className='notifyCount'>{notifyCount}</b>}
            <span className='colorFFF'>{title}</span>
        </NavLink>
    ) : (
        <div className='LeftNavigationItem FCCC selectNone'>
            {
                icon ? <i className={`fas ${icon} colorFFF`}></i> : <img src={image} />
            }
            {notifyCount && notifyCount != '0' && <b className='notifyCount'>{notifyCount}</b>}
            <span className='colorFFF'>{title}</span>
        </div>
    )
}

export default Nav;