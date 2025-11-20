import utils from '../../../utils.js';
import './FileCategoryShortcutItem.css';
import { useParams } from 'react-router';
import { useEffect, useState } from 'react';
import { apiRequest } from '../../../http_request.js';
import { useDispatch, useSelector } from "react-redux";
import { showPopup } from '../../../store/reducers/PopupSlice.js';
import { setSizeAndCount } from '../../../store/reducers/FileCategoryShortcutSlice.js';

const FileCategoryShortcutItem = ({ item }) => {
    const { title, route, active, size, count } = item,
        { icon, icon_color, bg_color } = utils.getIconBasedOnCategory(title);
    return (
        <div className={(active ? 'FileManagerItemActive ' : '') + 'FRCC FileCategoryShortcutList mR10 cursP'} style={{ backgroundColor: bg_color }}>
            <div className='icon_parent FRCC'>
                <i className={icon + " w_30 alignCenter"} style={{ color: icon_color }}></i>
            </div>
            <div className='name_count_size FCSS'>
                <span className='colorFFF'>{title}</span>
                <div className='FRSS w100 size_and_count mT5'>
                    {size != null && <span className='fs10 colorDDD mR5 bR_line' style={{ borderColor: '#aaa' }}>{utils.formatFileSize(size)}</span>}
                    {count != null && <span className='fs10 colorDDD mR5'>{count} items</span>}
                </div>
            </div>
        </div>
    )
}

const FileCategoryShortcut = () => {

    const dispatch = useDispatch();
    const { '*': params } = useParams();
    const [space, setSpace] = useState(null);
    const fileCategoryShortcut = useSelector(state => state.fileCategoryShortcut);

    useEffect(() => {
        if (!space) { return; }
        apiRequest(`/api/v1/workdrive/${space.toUpperCase()}/stats`, 'GET').then(({ data }) => {
            dispatch(setSizeAndCount(data));
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    }, [space]);

    useEffect(() => {
        const space = params?.split('/')[0];
        if (!space) { return; }
        setSpace(space);
    }, [params]);

    return (
        <div id='FileCategoryShortcut' className='FRCC mB10'>
            {fileCategoryShortcut.map((item, index) => <FileCategoryShortcutItem key={index} item={item} />)}
        </div>
    )
}

export default FileCategoryShortcut