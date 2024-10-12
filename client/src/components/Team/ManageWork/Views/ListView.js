import React from 'react'
import './ListView.css'

const ListviewHeader = ({ item }) => {
    const { totalRecords, currentPage, totalPages } = item;
    return <div className='FRCB w100 pB10' id='ListviewHeader'>
        <p className='FRCS' id='total_records'>Total Records <b>{totalRecords}</b></p>
        <div className='FRCE' id='pagination'>
            <i className='img_30_30 paginationIcon fa fa-chevron-left'></i>
            <select id='select_pages' value={currentPage}>
                {Array.from({ length: totalPages }, (_, i) => <option key={i} value={i + 1}>Page {i + 1}</option>)}
            </select>
            <i className='img_30_30 paginationIcon fa fa-chevron-right'></i>
        </div>
    </div>
}

const ListView = () => {
    const item = {
        totalRecords: 102,
        currentPage: 2,
        totalPages: 3
    }
    return (
        <div id='ListView'>
            <ListviewHeader item={item} />
            <table>

            </table>
        </div>
    )
}

export default ListView