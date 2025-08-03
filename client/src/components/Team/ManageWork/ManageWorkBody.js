import './ManageWorkBody.css'
import ManageWorkListKanban from './ManageWorkListKanban';
import { Navigate, Route, Routes, useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect, useState } from 'react';
import { closeFilterBox, showFilterBox } from '../../../store/reducers/ModuleFilterSlice';
import FilterContainer from './FilterContainer/FilterContainer';
import ListView from './Views/ListView';
import KanbanView from './Views/KanbanView';
import { useSearchParams } from 'react-router-dom';
import { apiRequest } from '../../../http_request';
import { setCurrentModuleId, setCurrentModuleView } from '../../../store/reducers/CurrentModuleSlice';
import { showPopup } from '../../../store/reducers/PopupSlice';
import PermissionDenied from '../../Auth/PermissionDenied';
import PageNotFound from '../../Auth/PageNotFound';
import Loader from '../../Loader';
import InternalServerError from '../../Auth/InternalServerError';

const NoRecordsAvailable = () => {

    return (
        <div className='FCCC w100' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-exclamation-triangle mR5'></i>
                No records found
            </p>
        </div>
    )
}

const ManageWorkBody = () => {

    const dispatch = useDispatch();
    const params = useParams();
    const { moduleId } = params;
    const view = params['*'] ? params['*'].split('/')[0] : 'list';
    const [searchParams, setSearchParams] = useSearchParams();

    const { filterBoxShowing } = useSelector((state) => state.moduleFilter);

    const [loader, setLoader] = useState(true);
    const [info, setInfo] = useState({});
    const [error, setError] = useState(null);
    const [records, setRecords] = useState([]);
    const [moduleNotFound, setModuleNotFound] = useState(false);
    const [permissionDenied, setPermissionDenied] = useState(false);

    const page = parseInt(searchParams.get('page') || '1', 10);

    const size = 3;

    useEffect(() => {
        if (filterBoxShowing) {
            dispatch(showFilterBox());
        } else {
            dispatch(closeFilterBox());
        }
    }, []);

    useEffect(() => {

        dispatch(setCurrentModuleView(view));
        dispatch(setCurrentModuleId(moduleId));
        setLoader(true);
        setRecords([]);
        setInfo({});
        apiRequest(`/api/v1/module/${moduleId}/records?page=${page - 1}&size=${size}`, 'GET').then(({ data, info }) => {
            setLoader(false);
            setInfo(info);
            setRecords(data);
        }).catch(({ message, statusCode }) => {
            setLoader(false);
            if (statusCode == 404) {
                setModuleNotFound(true);
            } else if (statusCode == 403) {
                setPermissionDenied(true);
            } else {
                dispatch(showPopup({ message, type: 'error' }));
                setError(true);
            }
        });
    }, [page, moduleId, view]);

    return loader ? <Loader /> :
        moduleNotFound ? <PageNotFound /> :
            permissionDenied ? <PermissionDenied /> :
                error ? <InternalServerError /> :
                    records.length ? (
                        <div id='ManageWorkBody' className='w100 custom-scrollbar FRSE'>
                            <div id='manage-work-slider' className={`FRSE ${filterBoxShowing ? 'filter-enabled' : ''}`}>

                                <FilterContainer />

                                <div id='views-render-frame'>
                                    <Routes>
                                        <Route path='/list' element={<ListView records={records} info={info} searchParams={searchParams} setSearchParams={setSearchParams} />} />
                                        <Route path='/kanban' element={<KanbanView ManageWorkListKanban={ManageWorkListKanban} />} />
                                        <Route path='/*' element={<Navigate to="/manage/module" />} />
                                    </Routes>
                                </div>
                            </div>
                        </div>
                    ) : <NoRecordsAvailable />
}

export default ManageWorkBody