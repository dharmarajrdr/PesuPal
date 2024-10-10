import React from 'react'
import FileCategoryShortcutList from './FileCategoryShortcutList.js'
import './FileCategoryShortcutItem.css';

const FileCategoryShortcutItem = ({ item }) => {
    const { title, icon, route, active, icon_color, size, count, bg_color } = item;
    return (
        <div className={(active ? 'FileManagerItemActive ' : '') + 'FRCC FileCategoryShortcutList mR10 cursP'} style={{ backgroundColor: bg_color }}>
            <div className='icon_parent FRCC'>
                <i className={icon + " w_30 alignCenter"} style={{ color: icon_color }}></i>
            </div>
            <div className='name_count_size FCSS'>
                <span className='colorFFF'>{title}</span>
                <div className='FRSS w100 pT5'>
                    <b className='fs10 colorFFF mR5 bR_line' style={{ borderColor: '#aaa' }}>{size}</b>
                    <b className='fs10 colorFFF mR5'>{count} items</b>
                </div>
            </div>
        </div>
    )
}

const FileCategoryShortcut = () => {
    return (
        <div id='FileCategoryShortcut' className='FRCC'>
            {FileCategoryShortcutList.map((item, index) => <FileCategoryShortcutItem key={index} item={item} />)}
        </div>
    )
}

export default FileCategoryShortcut