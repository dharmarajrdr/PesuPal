import React from 'react'
import { NavLink } from 'react-router-dom';
import './TeamLeftContainerItem.css'

const TeamLeftContainerItem = ({ item }) => {
    const { title, route, icon, notify_count } = item;
    return (
        <NavLink to={route} className={({ isActive }) => (isActive ? "active-tab" : "") + ' TeamLeftContainerItem FRCS w100 pX10 pY10 mx5'}>
            <i className={icon}></i>
            <div className='FRCB w100 pL10 title_notify'>
                <span className='color555 title'>{title}</span>
                {notify_count > 0 && <span className='color555 notify_count'>{notify_count}</span>}
            </div>
        </NavLink>
    )
}

export default TeamLeftContainerItem