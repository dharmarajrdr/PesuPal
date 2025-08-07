import { useSelector } from 'react-redux';
import PermissionDenied from '../../../Auth/PermissionDenied';
import './CreateRecordLayout.css';

const CreateRecordLayout = () => {

  const { data: currentModuleData } = useSelector((state) => state.currentModule);
  const { createRecord } = currentModuleData || { 'createRecord': false };

  return createRecord ? (
    <div id='create-new-record' className='w100 h100P'>

    </div>
  ) : <PermissionDenied />
}

export default CreateRecordLayout