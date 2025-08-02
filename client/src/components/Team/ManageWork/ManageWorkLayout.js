import './ManageWorkLayout.css'
import Header from './Header'
import { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import ManageWorkBody from './ManageWorkBody'
import { useParams } from 'react-router-dom'
import { showPopup } from '../../../store/reducers/PopupSlice';
import PageNotFound from '../../Auth/PageNotFound';
import Loader from '../../Loader';
import PermissionDenied from '../../Auth/PermissionDenied';
import InternalServerError from '../../Auth/InternalServerError';
import { apiRequest } from '../../../http_request';
import { setCurrentModuleData } from '../../../store/reducers/CurrentModuleSlice';

const ManageWorkLayout = () => {

    const { moduleId, view } = useParams();
    const dispatch = useDispatch();

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);
    const [moduleNotFound, setModuleNotFound] = useState(false);
    const [permissionDenied, setPermissionDenied] = useState(false);

    useEffect(() => {
        apiRequest(`/api/v1/module/${moduleId}`, "GET").then(({ data }) => {
            setLoading(false);
            dispatch(setCurrentModuleData(data));
        }).catch(({ message, statusCode }) => {
            setLoading(false);
            if (statusCode == 404) {
                setModuleNotFound(true);
            } else if (statusCode == 403) {
                setPermissionDenied(true);
            } else {
                dispatch(showPopup({ message, type: 'error' }));
                setError(true);
            }
        });
        return () => {
            dispatch(setCurrentModuleData({}));
        }
    }, [moduleId]);

    return loading ? <Loader /> :
        moduleNotFound ? <PageNotFound /> :
            permissionDenied ? <PermissionDenied /> :
                error ? <InternalServerError /> : (
                    <div id='ManageWorkLayout'>
                        <Header />
                        <ManageWorkBody />
                    </div>
                )
}

export default ManageWorkLayout