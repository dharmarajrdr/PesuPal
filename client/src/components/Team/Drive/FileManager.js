import React from 'react'
import { NavLink } from 'react-router-dom';
import utils from '../../../utils';
import FileManagerList from './FileManagerList'
import './FileManagerItem.css';

const FileManagerItem = ({ item }) => {
    const { id, title, route } = item,
        { icon_color, icon } = utils.getIconBasedOnCategory(title);
    return (
        <NavLink
            className={({ isActive }) => (isActive ? 'FileManagerItemActive ' : '') + 'FRCC FileManagerItem mR10'} to={route}>
            {({ isActive }) => (
                <>
                    <i className={icon + " pR5 w_20 alignCenter"} style={isActive ? {} : { color: icon_color }} ></i>
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
            <div>
                <div className='FRCC' id='uploadButton'>
                    <i className='fa fa-upload pR5 w_20'></i>
                    <span>Upload New</span>
                </div>
            </div>
        </div>
    )
}

export default FileManager