import './FileManagerItem.css';
import { NavLink } from 'react-router-dom';
import HeaderActions from './HeaderActions';
import FileManagerList from './FileManagerList';

const FileManagerItem = ({ item }) => {

    const { id, title, route, icon, color } = item;
    return (
        <NavLink
            className={({ isActive }) => (isActive ? 'FileManagerItemActive ' : '') + 'FRCC FileManagerItem mR10'} to={route}>
            {({ isActive }) => (
                <>
                    <i className={icon} style={isActive ? {} : { color }} ></i>
                    <span>{title}</span>
                </>
            )}
        </NavLink>
    )
}

const FileManager = () => {

    return (
        <div className='FRCB w100 mb20' id='FileManager'>
            <div className='FRCC'>
                {FileManagerList.map((item, index) => <FileManagerItem key={index} item={item} />)}
            </div>
            <HeaderActions />
        </div>
    )
}

export default FileManager