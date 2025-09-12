import Media from "../../../Media";
import { useNavigate } from "react-router";
import { apiRequest } from "../../../http_request";
import { useDispatch, useSelector } from "react-redux";
import BackNextCancelButtons from "./BackNextCancelButtons";
import { showPopup } from "../../../store/reducers/PopupSlice";
import { decrementStep } from "../../../store/reducers/CreateOrgSlice";
import { hideLoader, showLoader } from "../../../store/reducers/VerticalLoaderSlice";

const Step3 = ({ cancelButtonClicked }) => {

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const { steps } = useSelector(state => state.createOrg);

    const handleSubmit = async () => {

        const [step1Data, step2Data] = steps || [];

        const { displayName: orgDisplayName, uniqueName, displayPicture: orgDisplayPicture } = step1Data || {};
        const { displayName: userDisplayName, userName, displayPicture: userDisplayPicture } = step2Data || {};

        dispatch(showLoader());

        const orgData = {
            'displayName': orgDisplayName.trim(),
            'uniqueName': uniqueName.trim()
        };

        const userData = {
            'displayName': userDisplayName.trim(),
            'userName': userName.trim()
        };

        if (orgDisplayPicture) {
            try {
                const { data } = await Media.uploadSingleMedia({ 'file': orgDisplayPicture });
                const { mediaId } = data || {};
                Object.assign(orgData, { 'displayPicture': mediaId });
            } catch (error) {
                console.error('Error uploading org display picture:', error);
            }
        }

        if (userDisplayPicture) {
            try {
                const { data } = await Media.uploadSingleMedia({ 'file': userDisplayPicture });
                const { mediaId } = data || {};
                Object.assign(userData, { 'displayPicture': mediaId });
            } catch (error) {
                console.error('Error uploading user display picture:', error);
            }
        }

        apiRequest('/api/v1/org', 'POST', { org: orgData, user: userData }).then(async ({ data }) => {

            dispatch(showPopup({ message: `Organization created successfully!`, type: 'success' }));
            dispatch(hideLoader());
            navigate('/');

        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
            dispatch(hideLoader());
        });
    };

    const backButtonClicked = () => {

        dispatch(decrementStep());
    }

    return <div id='step-3' className="FCSC steps modal-box w100">
        <div className="FCSC">
            <h2>All Set!</h2>
            <p className="fs13" id="step-3-description">Click "Finish" to create your org.</p>
        </div>
        <BackNextCancelButtons cancelButtonClicked={cancelButtonClicked} backButtonClicked={backButtonClicked} handleSubmit={handleSubmit} submitButtonLabel={"Finish"} />
    </div >
}

export default Step3;