import React from 'react'
import { Link } from 'react-router-dom';
import './CustomModulesItem.css';

const CustomModulesItem = ({ item }) => {
    const { name, icon, icon_color, route, created_info, active } = item,
        { created_by, created_date } = created_info;
    return (
        <Link to={route} className={(active ? 'ActiveCustomView' : '') + ' CustomModulesItem mR10 FRCC'} title={"Created by: " + created_by + " on " + created_date}>
            <i className={icon + " pR10 img_20_20"} style={active ? {} : { color: icon_color }}></i>
            <span className='color555 pL5'>{name}</span>
        </Link>
    )
}

const CustomModules = ({ CustomModulesList }) => {
    return (
        <div id='CustomModules' className='FRCS'>
            {CustomModulesList.map((item, index) => <CustomModulesItem key={index} item={item} />)}
        </div>
    )
}

export default CustomModules