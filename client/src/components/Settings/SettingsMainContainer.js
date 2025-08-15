import { Navigate, Route, Routes } from 'react-router-dom'
import SubscriptionPlan from './subscription/SubscriptionPlan'
import PermissionDenied from '../Auth/PermissionDenied'
import ManagePeopleLayout from '../ManagePeople/ManagePeopleLayout'
import GeneralLayout from './general/GeneralLayout'

const SettingsMainContainer = ({ width }) => {

    return (
        <div id='settings-main-container' style={{ width }}>
            <Routes>
                <Route path="" element={<Navigate to="/settings/pricing" />} />
                <Route path="/general/*" element={<GeneralLayout />} />
                <Route path="/pricing/*" element={<SubscriptionPlan />} />
                <Route path='/manage-people/*' element={<ManagePeopleLayout />} />
                <Route path="*" element={<PermissionDenied />} />
            </Routes>
        </div>
    )
}

export default SettingsMainContainer