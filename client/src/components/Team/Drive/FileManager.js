import React from 'react'
import { Link } from 'react-router-dom';
import utils from '../../../utils';
import FileManagerList from './FileManagerList'
import './FileManagerItem.css';

const FileManagerItem = ({ item }) => {
    const { id, title, route, active } = item,
        { icon_color, icon } = utils.getIconBasedOnCategory(title);
    return (
        <Link className={(active ? 'FileManagerItemActive ' : '') + 'FRCC FileManagerItem mR10'} to={route}>
            <i className={icon + " pR5 w_20 alignCenter"} style={active ? {} : { color: icon_color }}></i>
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
                    <i className='fa fa-upload pR5 w_20'></i>
                    <span>Upload New</span>
                </div>
            </div>
        </div>
    )
}

export default FileManager