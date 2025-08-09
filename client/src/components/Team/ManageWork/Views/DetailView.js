import { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import { apiRequest } from '../../../../http_request';
import { showPopup } from '../../../../store/reducers/PopupSlice';
import Loader from '../../../Loader';
import RecordFormLayout from '../CreateRecord/RecordFormLayout';
import PermissionDenied from '../../../Auth/PermissionDenied';
import InternalServerError from '../../../Auth/InternalServerError';

const DetailView = ({ moduleId, recordId, setShowQuickDetailView, view, fieldsList, subject }) => {

    const dispatch = useDispatch();
    const [loader, setLoader] = useState(true);
    const [fields, setFields] = useState(fieldsList || []);
    const navigate = useNavigate();
    const params = useParams();
    view = view || 'create';
    moduleId = moduleId || params.moduleId;
    recordId = recordId || params.recordId;

    const { data: currentModuleData } = useSelector((state) => state.currentModule);
    const { readRecord } = currentModuleData || { 'readRecord': false };

    const onCancel = (e) => {
        e.preventDefault();
        e.stopPropagation();
        if (setShowQuickDetailView) {
            setShowQuickDetailView(false);
        } else {
            navigate(`/manage/module/${moduleId}/${view}`);
        }
    }

    const onExpand = (e) => {
        e.preventDefault();
        e.stopPropagation();
        navigate(`/manage/module/${moduleId}/create`);
    }

    useEffect(() => {
        if (fields.length > 0) {
            setLoader(false);
            return; // If fields are already provided, skip the API call
        }
        apiRequest(`/api/v1/module/record/${recordId}`, 'GET').then(({ data }) => {
            const { fields } = data || {};
            setLoader(false);
            setFields(fields);
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    }, []);

    return readRecord ? (
        <div id='create-new-record' className='w100 FCCS h100P'>
            {loader ? <Loader /> :
                subject ? <>
                    <div className='FRCB w100' id='create-new-record-header'>
                        <h1 id='subject-as-header'>{subject}</h1>
                        <div className='FRCE' id='new-record-actions'>
                            {view != 'create' && <i class="fa-solid fa-up-right-and-down-left-from-center mR10" id='expand-create-record-page' onClick={onExpand} title='Expand Create Record'></i>}
                            <button id='cancel-button' onClick={onCancel}>Cancel</button>
                            <button id='create-button'>Save</button>
                        </div>
                    </div>
                    <RecordFormLayout fields={fields} />
                </> : <InternalServerError />}
        </div>
    ) : <PermissionDenied />
}

export default DetailView