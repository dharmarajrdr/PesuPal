import { Navigate, Route, Routes } from 'react-router-dom'
import SubscriptionPlan from './subscription/SubscriptionPlan'

const SettingsMainContainer = ({ width }) => {

    return (
        <div id='settings-main-container' style={{ width }}>
            <Routes>
                <Route path="" element={<Navigate to="/settings/pricing" />} />
                <Route path="/pricing/*" element={<SubscriptionPlan />} />
            </Routes>
        </div>
    )
}

export default SettingsMainContainer