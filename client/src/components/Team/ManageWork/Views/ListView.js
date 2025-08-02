import React, { useEffect } from 'react'
import utils from '../../../../utils';
import { Link, useParams } from 'react-router-dom';
import './ListView.css'
import { setCurrentModuleId, setCurrentModuleView } from '../../../../store/reducers/CurrentModuleSlice';
import { useDispatch } from 'react-redux';

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

const widthChart = {
    "STRING": "350px",
    "DATE_TIME": "225px",
    "USER": "175px",
    "SELECT": "250px"
}

const ListviewHeader = ({ header }) => {
    return <div className='rows FRCS' id='listview_table_header'>
        {header.map(({ title, sort }, index) => {
            return <div className='col FRCS' key={index}>
                <b>{title.replace(/_/mg, ' ')}</b>
                {sort ?
                    <i className='fa fa-sort-down sortIcon'></i> :
                    <i className='fa fa-sort sortIcon'></i>
                }
            </div>
        })}
    </div>
}

const Column = ({ fieldType, data, index }) => {

    let content = null;

    switch (fieldType) {
        case 'DATE_TIME': {
            content = <span>{utils.convertDateAndTime(data)}</span>;
            break;
        }

        case 'STRING': {
            content = <span>{data}</span>;
            break;
        }

        case 'USER': {
            const { displayName, displayPicture } = data || {};
            content = <>
                {displayPicture && <img src={displayPicture} className='img_20_20 mR10' />}
                <span>{displayName}</span>
            </>;
            break;
        }

        case 'SELECT': {
            if (Array.isArray(data)) {
                content = data.map(({ value }, idx) => (
                    <span key={idx} className="mR5 typeSELECT">{value}</span>
                ));
            } else {
                content = <span className="typeSELECT">{data}</span>;
            }
            break;
        }
        default: {
            content = <span>{data}</span>;
        }
    }

    return (
        <div className='col FRCS' key={index} style={{ 'width': widthChart[fieldType] }}>
            {content}
        </div>
    );
};

const Row = ({ item, item_index }) => {

    const { moduleId, recordId, fields } = item;
    const route = `/manage/module/${moduleId}/record/${recordId}`;

    return <Link to={route} className='rows FRCS' key={item_index}>
        {fields?.map(({ data, fieldType }, index) => {
            return <Column fieldType={fieldType} data={data} index={index} />
        })}
    </Link>
}


const ListviewBody = ({ data }) => {

    return <>
        {
            data.map((item, item_index) => {
                return <Row item={item} key={item_index} />
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

    const dispatch = useDispatch();
    const { moduleId } = useParams();

    useEffect(() => {
        dispatch(setCurrentModuleView("list"));
        dispatch(setCurrentModuleId(moduleId));
    }, []);

    return (
        <div id='ListView'>
            <ListviewTopHeader item={item} />
            <div id='listview_table' className='custom-scrollbar'>
                <ListviewHeader header={header} />
                <ListviewBody data={data} />
            </div>
        </div>
    )
}

export default ListView