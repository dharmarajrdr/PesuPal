import { NavLink } from 'react-router-dom';
import '../Team/TeamLeftContainerItem.css'

const SettingsLeftContainerItem = ({ item, leftNavOpened }) => {
    const { title, route, icon, notify_count } = item;
    return (
        <NavLink to={route} className={({ isActive }) => (isActive ? "active-tab" : "") + ' TeamLeftContainerItem FRCS w100 pX10 pY10 mx5'} title={leftNavOpened ? null : title}>
            <div className='pR'>
                <i className={icon}></i>
                {!leftNavOpened && notify_count > 0 && <span className='notifyCount'>{notify_count}</span>}
            </div>
            <div className='FRCB w100 pL10 title_notify'>
                <span className='color555 title'>{title}</span>
                {leftNavOpened && notify_count > 0 && <span className='color555 notify_count'>{notify_count}</span>}
            </div>
        </NavLink>
    )
}

export default SettingsLeftContainerItem