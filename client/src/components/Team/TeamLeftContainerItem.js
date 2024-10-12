import React from 'react'
import { Link } from 'react-router-dom';
import './TeamLeftContainerItem.css'

const TeamLeftContainerItem = ({ item }) => {
    const { title, route, icon, notify_count } = item;
    return (
        <Link to={route} className='TeamLeftContainerItem FRCS w100 pX10 pY10 mx5'>
            <i className={icon}></i>
            <div className='FRCB w100 pL10 title_notify'>
                <span className='color555 title'>{title}</span>
                {notify_count > 0 && <span className='color555 notify_count'>{notify_count}</span>}
            </div>
        </Link>
    )
}

export default TeamLeftContainerItem