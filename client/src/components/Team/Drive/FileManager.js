import React from 'react'
import { Link } from 'react-router-dom';
import FileManagerList from './FileManagerList'
import './FileManagerItem.css';

const FileManagerItem = ({ item }) => {
    const { id, title, icon, route, active, icon_color } = item;
    return (
        <Link className={(active ? 'FileManagerItemActive ' : '') + 'FRCC FileManagerItem mR10'} to={route}>
            <i className={icon + " pR5"} style={active ? {} : { color: icon_color }}></i>
            <span>{title}</span>
        </Link>
    )
}

const FileManager = () => {
    return (
        <div className='FRCB w100' id='FileManager'>
            <div className='FRCC'>
                {FileManagerList.map((item, index) => <FileManagerItem key={index} item={item} />)}
            </div>
            <div>
                <div className='FRCC' id='uploadButton'>
                    <i className='fa fa-upload pR5'></i>
                    <span>Upload New</span>
                </div>
            </div>
        </div>
    )
}

export default FileManager