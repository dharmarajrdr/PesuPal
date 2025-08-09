import { useDispatch, useSelector } from 'react-redux';
import PermissionDenied from '../../../Auth/PermissionDenied';
import './CreateRecordLayout.css';
import RecordFormLayout from './RecordFormLayout';
import { useNavigate, useParams } from 'react-router-dom';
import Loader from '../../../Loader';
import { useEffect, useState } from 'react';
import { apiRequest } from '../../../../http_request';
import { showPopup } from '../../../../store/reducers/PopupSlice';

const CreateRecordLayout = ({ moduleId, setShowQuickCreateRecord, view }) => {

    const dispatch = useDispatch();
    const [loader, setLoader] = useState(true);
    const [fields, setFields] = useState([]);
    const navigate = useNavigate();
    const params = useParams();
    view = view || 'create';
    moduleId = moduleId || params.moduleId;

    const { data: currentModuleData } = useSelector((state) => state.currentModule);
    const { createRecord } = currentModuleData || { 'createRecord': false };

    const onCancel = (e) => {
        e.preventDefault();
        e.stopPropagation();
        if (setShowQuickCreateRecord) {
            setShowQuickCreateRecord(false);
        } else {
            navigate(`/manage/module/${moduleId}/list`);
        }
    }

    const onExpand = (e) => {
        e.preventDefault();
        e.stopPropagation();
        navigate(`/manage/module/${moduleId}/create`);
    }

    useEffect(() => {
        apiRequest(`/api/v1/module/${moduleId}/fields`, 'GET').then(({ data }) => {
            setLoader(false);
            setFields(data);
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    }, []);

    return createRecord ? (
        <div id='create-new-record' className='w100 FCCS h100P'>
            {loader ? <Loader /> : <>
                <div className='FRCB w100' id='create-new-record-header'>
                    <h1 id='new-record-title'>New Record</h1>
                    <div className='FRCE' id='new-record-actions'>
                        {view != 'create' && <i class="fa-solid fa-up-right-and-down-left-from-center mR10" id='expand-create-record-page' onClick={onExpand} title='Expand Create Record'></i>}
                        <button id='cancel-button' onClick={onCancel}>Cancel</button>
                        <button id='create-button'>Create</button>
                    </div>
                </div>
                <RecordFormLayout fields={fields} componentType='create' />
            </>}
        </div>
    ) : <PermissionDenied />
}

export default CreateRecordLayout