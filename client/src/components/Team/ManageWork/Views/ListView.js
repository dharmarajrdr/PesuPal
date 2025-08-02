import { useEffect, useState } from 'react'
import utils from '../../../../utils';
import { Link, useParams } from 'react-router-dom';
import './ListView.css'
import { setCurrentModuleId, setCurrentModuleView } from '../../../../store/reducers/CurrentModuleSlice';
import { useDispatch } from 'react-redux';
import { apiRequest } from '../../../../http_request';
import Loader from '../../../Loader';

const ListviewTopHeader = ({ item }) => {
    const { totalRecords, page } = item;
    return <div className='FRCB w100 pB10' id='ListviewHeader'>
        <p className='FRCS' id='total_records'>Total Records <b>{totalRecords}</b></p>
        <div className='FRCE' id='pagination'>
            <i className='img_30_30 paginationIcon fa fa-chevron-left'></i>
            <select id='select_pages' defaultValue={page}>
                {Array.from({ length: 10 }, (_, i) => <option key={i} value={i + 1}>Page {i + 1}</option>)}
            </select>
            <i className='img_30_30 paginationIcon fa fa-chevron-right'></i>
        </div>
    </div>
}

const widthChart = {
    "STRING": "350px",
    "DATE_TIME": "225px",
    "USER": "225px",
    "SELECT": "250px"
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
                    selected ? <span key={idx} className="mR5 typeSELECT">{value}</span> : null
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
            return <Column fieldType={fieldType} data={data} key={index} />
        })}
    </Link>
}


const ListviewBody = ({ data }) => <>
    {
        data.map((item, item_index) => <Row item={item} key={item_index} item_index={item_index} />)
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

const ListView = () => {

    const dispatch = useDispatch();
    const { moduleId } = useParams();
    const [loader, setLoader] = useState(true);
    const [error, setError] = useState(null);
    const [records, setRecords] = useState([]);
    const [info, setInfo] = useState({});

    useEffect(() => {
        console.log('ListView mounted');
        dispatch(setCurrentModuleView("list"));
        dispatch(setCurrentModuleId(moduleId));
        apiRequest(`/api/v1/module/${moduleId}/records?page=0&size=25`, 'GET').then(({ data, info }) => {
            setLoader(false);
            setInfo(info);
            setRecords(data);
        }).catch(({ message }) => {
            setLoader(false);
            setError(message);
        });
    }, []);

    const header = generateHeader({ fields: records[0]?.fields || [] });

    return loader ? <Loader /> :
        records.length ? (
            <div id='ListView'>
                <ListviewTopHeader item={info} />
                <div id='listview_table' className='custom-scrollbar'>
                    <ListviewHeader header={header} />
                    <ListviewBody data={records} />
                </div>
            </div>
        ) : <NoRecordsAvailable />
}

export default ListView