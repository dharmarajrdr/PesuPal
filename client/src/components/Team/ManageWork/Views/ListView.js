import { useEffect, useState } from 'react'
import utils from '../../../../utils';
import { Link, useParams } from 'react-router-dom';
import './ListView.css'
import { useSearchParams } from 'react-router-dom';
import { setCurrentModuleId, setCurrentModuleView } from '../../../../store/reducers/CurrentModuleSlice';
import { useDispatch } from 'react-redux';
import { apiRequest } from '../../../../http_request';
import Loader from '../../../Loader';
import { showPopup } from '../../../../store/reducers/PopupSlice';

const ListviewTopHeader = ({ item, searchParams, setSearchParams }) => {

    const { totalRecords, page, hasMoreRecords, size } = item;
    const totalPagesCount = Math.ceil(totalRecords / size);
    const dispatch = useDispatch();

    const pageSelected = (pageNumber) => {

        if (pageNumber < 1) {
            return dispatch(showPopup({ message: 'Invalid page number', type: 'error' }));
        }

        // Update just the page param (preserving others if needed)
        const updatedParams = new URLSearchParams(searchParams);
        updatedParams.set('page', pageNumber);
        setSearchParams(updatedParams);
    };

    const leftArrowClicked = () => {
        if (page >= 1) {
            pageSelected(page);
        } else {
            return dispatch(showPopup({ message: 'No previous pages available', type: 'error' }));
        }
    }

    const rightArrawClicked = () => {
        if (hasMoreRecords) {
            pageSelected(page + 2);
        } else {
            return dispatch(showPopup({ message: 'No more pages available', type: 'error' }));
        }
    };


    return <div className='FRCB w100 pB10' id='ListviewHeader'>
        <div className='FRCS'>
            <p className='FRCS' id='total_records'>Total Records <b>{totalRecords}</b></p>
            <p className='FRCS mL10 pL10' id='records_per_page'>Records Per Page: <b>{size}</b></p>
        </div>
        <div className='FRCE' id='pagination'>
            <i className='img_30_30 paginationIcon fa fa-chevron-left' onClick={leftArrowClicked}></i>
            <select id='select_pages' value={page + 1} onChange={(e) => pageSelected(e.target.value)}>
                {Array.from({ length: totalPagesCount }, (_, i) => <option key={i} value={i + 1}>Page {i + 1}</option>)}
            </select>
            <i className='img_30_30 paginationIcon fa fa-chevron-right' onClick={rightArrawClicked}></i>
        </div>
    </div>
}

const widthChart = {
    "STRING": "350px",
    "DATE_TIME": "225px",
    "USER": "225px",
    "SELECT": "250px",
    "TEXT": "350px",
    "LINK": "250px"
}

const ListviewHeader = ({ header }) => {
    return <div className='rows FRCS' id='listview_table_header'>
        {header.map(({ fieldName, fieldType, sort }, index) => {
            return <div className='col FRCS' key={index} style={{ 'width': widthChart[fieldType] }}>
                <b>{fieldName}</b>
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
            content = data ? <span>{utils.convertDateAndTime(data)}</span> : null;
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
                content = data.map(({ value, selected }, idx) => (
                    selected ? <span key={idx} className="mR5 typeSELECT" style={{ backgroundColor: utils.uniqueColorGenerator(value)}}>{value}</span> : null
                ));
            } else {
                content = <span className="typeSELECT" style={{ backgroundColor: utils.uniqueColorGenerator(data), color: '#fff' }}>{data}</span>;
            }
            break;
        }
        case 'TEXT': {
            content = <span>{data}</span>;
            break;
        }
        case 'LINK': {
            const { url, title } = data || {};
            content = url && (
                <span className='FRCS link-wrapper' onClick={(e) => { e.stopPropagation(); window.open(url, '_blank', 'noopener,noreferrer'); }}>
                    <i className='fa fa-link mR5 colorAAA'></i>{title}
                </span>
            );
            break;
        }
        default: {
            content = <span>Unable to display</span>;
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
            return <Column fieldType={fieldType} data={data} key={index} />
        })}
    </Link>
}


const ListviewBody = ({ records }) => <>
    {
        records.map((item, item_index) => <Row item={item} key={item_index} item_index={item_index} />)
    }
</>


const generateHeader = ({ fields }) => fields.map(({ fieldName, fieldType }) => ({ fieldName, fieldType }));


const NoRecordsAvailable = () => {

    return (
        <div className='FCCC w100 h100' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-exclamation-triangle mR5'></i>
                No records found
            </p>
        </div>
    )
}

const ListViewTable = ({ records }) => {

    const header = generateHeader({ fields: records[0]?.fields || [] });

    return records.length ? <div id='listview_table' className='custom-scrollbar'>
        <ListviewHeader header={header} />
        <ListviewBody records={records} />
    </div> : <NoRecordsAvailable />
}

const ListView = () => {

    const [loader, setLoader] = useState(true);
    const [info, setInfo] = useState({});

    const [searchParams, setSearchParams] = useSearchParams();
    const dispatch = useDispatch();
    const { moduleId } = useParams();
    const [error, setError] = useState(null);
    const [records, setRecords] = useState([]);

    const page = parseInt(searchParams.get('page') || '1', 10);

    const size = 3;

    useEffect(() => {
        console.log('ListView mounted');
        dispatch(setCurrentModuleView("list"));
        dispatch(setCurrentModuleId(moduleId));
        apiRequest(`/api/v1/module/${moduleId}/records?page=${page - 1}&size=${size}`, 'GET').then(({ data, info }) => {
            setLoader(false);
            setInfo(info);
            setRecords(data);
        }).catch(({ message }) => {
            setLoader(false);
            setError(message);
        });
    }, [page]);

    return loader ? <Loader /> :

        <div id='ListView'>
            <ListviewTopHeader item={info} searchParams={searchParams} setSearchParams={setSearchParams} />
            <ListViewTable records={records} />
        </div>
}

export default ListView