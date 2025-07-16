import { Navigate, Route, Routes } from 'react-router-dom'
import SubscriptionPlan from './subscription/SubscriptionPlan'
import PageNotFound from '../Auth/PageNotFound'
import PermissionDenied from '../Auth/PermissionDenied'

const SettingsMainContainer = ({ width }) => {

    return (
        <div id='settings-main-container' style={{ width }}>
            <Routes>
                <Route path="" element={<Navigate to="/settings/pricing" />} />
                <Route path="/pricing/*" element={<SubscriptionPlan />} />
                <Route path="*" element={<PermissionDenied />} />
            </Routes>
        </div>
    )
}

export default SettingsMainContainer