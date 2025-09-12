import { useState } from "react";
import ImageUploader from "../../ImageUploader";
import { useDispatch, useSelector } from "react-redux";
import BackNextCancelButtons from "./BackNextCancelButtons";
import { showPopup } from "../../../store/reducers/PopupSlice";
import { incrementStep, updateStep } from "../../../store/reducers/CreateOrgSlice";
import { useNavigate } from "react-router";

const Step1 = ({ cancelButtonClicked }) => {

    const navigate = useNavigate();
    const dispatch = useDispatch();

    const createOrg = useSelector(state => state.createOrg);
    const { steps } = createOrg || { steps: [{}] };
    const stepData = steps[0] || {};
    const [displayName, setDisplayName] = useState(stepData.displayName);
    const [uniqueName, setUniqueName] = useState(stepData.uniqueName);
    const [displayPicture, setDisplayPicture] = useState(stepData.displayPicture);

    const backButtonClicked = () => {

        navigate("/");
    }

    const nextStep = () => {

        if (!displayName.trim()) {
            return dispatch(showPopup({ message: 'Display Name is required', type: 'error' }));
        }

        if (!uniqueName.trim()) {
            return dispatch(showPopup({ message: 'Unique Name is required', type: 'error' }));
        }

        dispatch(updateStep({ step: 0, data: { displayName: displayName.trim(), uniqueName: uniqueName.trim(), displayPicture: displayPicture || null } }));

        dispatch(incrementStep());
    }

    return <div id='step-1' className="FCCC steps modal-box w100">

        <div className='FCCC w100'>
            <ImageUploader defaultImage={displayPicture && URL.createObjectURL(displayPicture)} onImageSelect={setDisplayPicture} placeholder={"Logo"} style={{ width: "100px", height: "100px", marginBottom: "10px" }} />
            <input type="text" placeholder="Display Name (e.g., Amazon)" value={displayName} onChange={(e) => setDisplayName(e.target.value)} className="org-input" />
            <input type="text" placeholder="Unique Name (e.g., amazon)" value={uniqueName} onChange={(e) => setUniqueName(e.target.value)} className="org-input" />
        </div>

        <BackNextCancelButtons backButtonClicked={backButtonClicked} handleSubmit={nextStep} cancelButtonClicked={cancelButtonClicked} />
    </div >
}

export default Step1;