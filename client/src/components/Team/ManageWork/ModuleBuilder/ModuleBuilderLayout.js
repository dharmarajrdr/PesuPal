import { useEffect, useState } from 'react'
import ModuleBuilderHeader from './ModuleBuilderHeader'
import Loader from '../../../Loader';
import PermissionDenied from '../../../Auth/PermissionDenied';
import PageNotFound from '../../../Auth/PageNotFound';
import { apiRequest } from '../../../../http_request';
import { useParams } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { showPopup } from '../../../../store/reducers/PopupSlice';
import InternalServerError from '../../../Auth/InternalServerError';
import { setCurrentModuleData } from '../../../../store/reducers/CurrentModuleSlice';

const ModuleBuilderLayout = () => {

    const params = useParams();
    const dispatch = useDispatch();
    const { moduleId } = params || {};

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
    }, []);

    return loading ? <Loader /> :
        moduleNotFound ? <PageNotFound /> :
            permissionDenied ? <PermissionDenied /> :
                error ? <InternalServerError /> : (
                    <div id='module-builder-layout' className='Layout'>
                        <ModuleBuilderHeader />
                    </div>
                )
}

export default ModuleBuilderLayout