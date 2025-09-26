import { NavLink } from 'react-router-dom';
import './FeedsLeftPanelItem.css'

const FeedsLeftPanelItem = ({ item, leftNavOpened }) => {

    const { icon, title, color, route, participants } = item;

    return (
        <NavLink end={route === "/feeds"} to={route} className={({ isActive }) => `FRCS w100${isActive ? ' active' : ''}`} id='FeedsLeftPanelItem' title={leftNavOpened ? null : title}>
            <i className={icon} style={{ color }} ></i>
            <span className='noTextWrap'>{title}</span>
            {leftNavOpened && <span>{participants}</span>}
        </NavLink>
    )
}

export default FeedsLeftPanelItem