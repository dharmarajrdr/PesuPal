import Step1 from './Step1';
import Step2 from './Step2';
import Step3 from './Step3';
import './CreateOrgModal.css';
import { useNavigate } from 'react-router-dom';
import ProgressSteps from '../../../ProgressSteps';
import { useDispatch, useSelector } from 'react-redux';
import { resetAllSteps } from '../../../store/reducers/CreateOrgSlice';

const CreateOrgModal = () => {

    const { currentStep } = useSelector(state => state.createOrg);

    const navigate = useNavigate();
    const dispatch = useDispatch();

    const cancelButtonClicked = () => {
        dispatch(resetAllSteps());
        navigate("/");
    }

    const getComponentForStep = (step) => {
        switch (step) {
            case 0: return <Step1 cancelButtonClicked={cancelButtonClicked} />;
            case 1: return <Step2 cancelButtonClicked={cancelButtonClicked} />;
            case 2: return <Step3 cancelButtonClicked={cancelButtonClicked} />;
            default: return null;
        }
    };

    return (
        <div id="create-org-modal" className="FCCC">
            <div className='FCCC' id='create-org-modal-content'>
                <ProgressSteps steps={["Org Details", "User Details", "Finish", "Verify"]} currentStep={currentStep} />
                {getComponentForStep(currentStep)}
            </div>
        </div>
    );
};

export default CreateOrgModal;
