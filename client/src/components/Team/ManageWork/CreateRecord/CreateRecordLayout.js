import { useSelector } from 'react-redux';
import PermissionDenied from '../../../Auth/PermissionDenied';
import './CreateRecordLayout.css';
import RecordFormLayout from './RecordFormLayout';
import { useNavigate, useParams } from 'react-router-dom';

const CreateRecordLayout = () => {

  const { moduleId } = useParams();
  const navigate = useNavigate();

  const { data: currentModuleData } = useSelector((state) => state.currentModule);
  const { createRecord } = currentModuleData || { 'createRecord': false };

  const onCancel = (e) => {
    e.preventDefault();
    e.stopPropagation();
    navigate(`/manage/module/${moduleId}/list`);
  }

  return createRecord ? (
    <div id='create-new-record' className='w100 FCCS h100P'>
      <div className='FRCB w100'>
        <h1 id='new-record-title'>New Record</h1>
        <div className='FRCE' id='new-record-actions'>
          <button id='cancel-button' onClick={onCancel}>Cancel</button>
          <button id='create-button'>Create</button>
        </div>
      </div>
      <RecordFormLayout />
    </div>
  ) : <PermissionDenied />
}

export default CreateRecordLayout