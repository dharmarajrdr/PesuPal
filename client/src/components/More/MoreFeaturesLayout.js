import MoreFeaturesList from "./MoreFeaturesList"
import './MoreFeaturesLayout.css';
import { Route, Routes } from "react-router-dom";
import ReachSupportLayout from "../Support/ReachSupportLayout";
import PageNotFound from "../Auth/PageNotFound";

const MoreFeaturesLayout = () => {

    return (
        <div id='more-features-layout' className="Layout">
            <Routes>
                <Route path="/" element={<MoreFeaturesList />} />
                <Route path="/reach-support/*" element={<ReachSupportLayout />} />
                <Route path="*" element={<PageNotFound />} />
            </Routes>
        </div>
    )
}

export default MoreFeaturesLayout