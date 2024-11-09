import React from 'react'
import { Link } from 'react-router-dom';
import './FeedsLeftPanelItem.css'

const FeedsLeftPanelItem = ({ item, leftNavOpened }) => {
    const { icon, title, is_active, color, visibility, route, participants } = item;
    return (
        <Link to={route} id='FeedsLeftPanelItem' className={`FRCS w100 ${is_active ? 'active' : ''}`} title={leftNavOpened ? null : title}>
            <i className={icon} style={{ color }} ></i>
            <span className='noTextWrap'>{title}</span>
            {leftNavOpened && <span>{participants}</span>}
        </Link>
    )
}

export default FeedsLeftPanelItem