import React, { useEffect } from 'react'
import utils from '../../../../utils';
import { Link } from 'react-router-dom';
import './ListView.css'
import Profile from '../../../OthersProfile/Profile';
import SomeProfile from '../../../OthersProfile/SomeProfile';
import { setCurrentModuleView } from '../../../../store/reducers/CurrentModuleViewSlice';

const ListviewTopHeader = ({ item }) => {
    const { totalRecords, currentPage, totalPages } = item;
    return <div className='FRCB w100 pB10' id='ListviewHeader'>
        <p className='FRCS' id='total_records'>Total Records <b>{totalRecords}</b></p>
        <div className='FRCE' id='pagination'>
            <i className='img_30_30 paginationIcon fa fa-chevron-left'></i>
            <select id='select_pages' defaultValue={currentPage}>
                {Array.from({ length: totalPages }, (_, i) => <option key={i} value={i + 1}>Page {i + 1}</option>)}
            </select>
            <i className='img_30_30 paginationIcon fa fa-chevron-right'></i>
        </div>
    </div>
}

const ListviewHeader = ({ header }) => {
    return <div className='rows FRCS' id='listview_table_header'>
        {header.map(({ title, sort, width }, index) => {
            return <div className='col FRCS' key={index} style={{ minWidth: width }}>
                <b>{title.replace(/_/mg, ' ')}</b>
                {sort ?
                    <i className='fa fa-sort-down sortIcon'></i> :
                    <i className='fa fa-sort sortIcon'></i>
                }
            </div>
        })}
    </div>
}

const ListviewBody = ({ header, data, setShowProfile }) => {

    const handleProfile = (e) => {
        e.preventDefault();
        setShowProfile(true);
    }

    return <>
        {
            data.map((item, item_index) => {
                return <Link to={item.route} className='rows FRCS' key={item_index}>
                    {header.map(({ title, type, width }, index) => {
                        const value = item[title.toLowerCase()];
                        if (type == 'object') {
                            const { image, name } = value || {}, icon_info = {};
                            if (title == 'Tag') {
                                Object.assign(icon_info, utils.getIconForTagWithColor(name) || {});
                            } else if (title == 'Priority') {
                                Object.assign(icon_info, utils.getPriortyColorAndIcon(name) || {});
                            }
                            const { icon, icon_color } = icon_info;
                            return <div className='col FRCS' key={index} style={{ minWidth: width }}>
                                {image && <img src={image} className='img_20_20 mR10' onClick={handleProfile} />}
                                {icon && <i className={icon + ' img_20_20 mR5 alignCenter'} style={{ color: icon_color }}></i>}
                                <span>{name}</span>
                            </div>
                        }
                        return <div className='col FRCS' key={index} style={{ minWidth: width }}>
                            <span>{value}</span>
                        </div>
                    })}
                </Link>
            })
        }
    </>
}

const ListView = ({ ManageWorkList }) => {

    const item = {
        totalRecords: 102,
        currentPage: 2,
        totalPages: 3
    }, { header, data } = ManageWorkList,
        [showProfile, setShowProfile] = React.useState(false);

    useEffect(() => {
        setCurrentModuleView("list");
    }, []);

    return (
        <div id='ListView'>
            <ListviewTopHeader item={item} />
            <div id='listview_table' className='custom-scrollbar'>
                <ListviewHeader header={header} />
                <ListviewBody header={header} data={data} setShowProfile={setShowProfile} />
            </div>
            {showProfile && <Profile Profile={SomeProfile} setShowProfile={setShowProfile} />}
        </div>
    )
}

export default ListView