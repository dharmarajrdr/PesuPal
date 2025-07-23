import { NavLink } from 'react-router-dom';
import './LeftNavigation.css'
import UserAvatar from '../User/UserAvatar';

const NavContent = ({ icon, image, notifyCount, title }) => {
    return <>
        {
            icon ? <i className={`fas ${icon} colorFFF`}></i> : <UserAvatar displayPicture={image} />
        }
        {notifyCount && notifyCount != '0' && <b className='notifyCount'>{notifyCount}</b>}
        <span className='colorFFF'>{title}</span>
    </>
}

const Nav = ({ icon, image, title, route, notifyCount, showOrgListHandler }) => {

    return route ? (
        <NavLink to={route} className={({ isActive }) => (isActive ? 'activeNav' : '') + ` LeftNavigationItem cursP FCCC selectNone`}  >
            <NavContent icon={icon} image={image} notifyCount={notifyCount} title={title} />
        </NavLink>
    ) : (
        <div className='LeftNavigationItem FCCC selectNone' onClick={showOrgListHandler} >
            <NavContent icon={icon} image={image} notifyCount={notifyCount} title={title} />
        </div>
    )
}

export default Nav;