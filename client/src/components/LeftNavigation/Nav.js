import { NavLink } from 'react-router-dom';
import './LeftNavigation.css'

const NavContent = ({ icon, image, notifyCount, title }) => {
    console.log({ icon, image });
    return <>
        {
            icon ? <i className={`fas ${icon} colorFFF`}></i> : <img src={image} />
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