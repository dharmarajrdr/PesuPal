import { useState } from "react";
import ImageUploader from "../../ImageUploader";
import { useDispatch, useSelector } from "react-redux";
import BackNextCancelButtons from "./BackNextCancelButtons";
import { showPopup } from "../../../store/reducers/PopupSlice";
import { decrementStep, incrementStep, updateStep } from "../../../store/reducers/CreateOrgSlice";

const Step2 = () => {

    const dispatch = useDispatch();

    const createOrg = useSelector(state => state.createOrg);
    const { steps } = createOrg || { steps: [{}] };
    const stepData = steps[1] || {};
    const [displayName, setDisplayName] = useState(stepData.displayName);
    const [userName, setUserName] = useState(stepData.userName);
    const [displayPicture, setDisplayPicture] = useState(stepData.displayPicture);

    const backButtonClicked = () => {

        dispatch(decrementStep());
    }

    const nextStep = () => {

        if (!displayName.trim()) {
            return dispatch(showPopup({ message: 'Display Name is required', type: 'error' }));
        }

        if (!userName.trim()) {
            return dispatch(showPopup({ message: 'User Name is required', type: 'error' }));
        }

        dispatch(updateStep({ step: 1, data: { displayName: displayName.trim(), userName: userName.trim(), displayPicture: displayPicture || null } }));

        dispatch(incrementStep());
    }

    return <div id='step-2' className="FCCC steps modal-box w100">
        <div className='FCCC w100'>
            <ImageUploader defaultImage={displayPicture && URL.createObjectURL(displayPicture)} onImageSelect={setDisplayPicture} placeholder={"Image"} style={{ width: "100px", height: "100px", marginBottom: "10px" }} />
            <input type="text" placeholder="Display Name (e.g., John Doe)" value={displayName} onChange={(e) => setDisplayName(e.target.value)} className="org-input" />
            <input type="text" placeholder="User Name (e.g., john_doe)" value={userName} onChange={(e) => setUserName(e.target.value)} className="org-input" />
        </div>

        <BackNextCancelButtons backButtonClicked={backButtonClicked} handleSubmit={nextStep} />
    </div >
}

export default Step2;