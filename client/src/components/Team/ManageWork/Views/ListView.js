import React from 'react'
import { Link } from 'react-router-dom';
import './ListView.css'

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

const ListviewBody = ({ header, data }) => {
    return <>
        {
            data.map((item, item_index) => {
                return <Link to={item.route} className='rows FRCS' key={item_index}>
                    {header.map(({ title, type, width }, index) => {
                        const value = item[title.toLowerCase()];
                        if (type == 'object') {
                            return <div className='col FRCS' key={index} style={{ minWidth: width }}>
                                <img src={value.image} className='img_20_20 mR10' />
                                <span>{value.name}</span>
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
    }, { header, data } = ManageWorkList;
    return (
        <div id='ListView'>
            <ListviewTopHeader item={item} />
            <div id='listview_table' className='custom-scrollbar'>
                <ListviewHeader header={header} />
                <ListviewBody header={header} data={data} />
            </div>
        </div>
    )
}

export default ListView