import GeneralLayout from './general/GeneralLayout'
import AccountLayout from './account/AccountLayout'
import PermissionDenied from '../Auth/PermissionDenied'
import OrgRolesLayout from '../OrgRules/OrgRolesLayout'
import { Navigate, Route, Routes } from 'react-router-dom'
import SubscriptionPlan from './subscription/SubscriptionPlan'
import ManagePeopleLayout from '../ManagePeople/ManagePeopleLayout'

const SettingsMainContainer = ({ width }) => {

    return (
        <div id='settings-main-container' style={{ width }}>
            <Routes>
                <Route path="" element={<Navigate to="/settings/general" />} />
                <Route path="/general/*" element={<GeneralLayout />} />
                <Route path="/account/*" element={<AccountLayout />} />
                <Route path="/pricing/*" element={<SubscriptionPlan />} />
                <Route path='/manage-people/*' element={<ManagePeopleLayout />} />
                <Route path='/roles-permissions' element={<OrgRolesLayout />} />
                <Route path="*" element={<PermissionDenied />} />
            </Routes>
        </div>
    )
}

export default SettingsMainContainer