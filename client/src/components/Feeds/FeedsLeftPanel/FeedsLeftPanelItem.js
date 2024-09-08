import React from 'react'
import './FeedsLeftPanelItem.css'

const FeedsLeftPanelItem = ({ item }) => {
    const { icon, title, is_active, color } = item;
    return (
        <div id='FeedsLeftPanelItem' className={`FRCS w100 ${is_active ? 'active' : ''}`}>
            <i className={icon} style={{ color }} ></i>
            <span>{title}</span>
        </div>
    )
}

export default FeedsLeftPanelItem