import React from 'react'
import { Link } from 'react-router-dom';
import './FeedsLeftPanelPagesItem.css'

const FeedsLeftPanelPagesItem = ({ item }) => {
    const { icon, title, is_active, color, visibility, route, participants } = item;
    return (
        <Link to={route} id='FeedsLeftPanelPagesItem' className={`FRCB ${is_active ? 'active' : ''}`}>
            <div className='icon_pagetitle'>
                <i className={icon} style={{ color }} ></i>
                <span className='title'>{title}</span>
            </div>
            <span className='participants_count'>{participants}</span>
        </Link>
    )
}

export default FeedsLeftPanelPagesItem