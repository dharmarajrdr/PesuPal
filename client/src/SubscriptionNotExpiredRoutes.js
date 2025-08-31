import { useEffect } from 'react';
import TeamLayout from './components/Team/TeamLayout';
import ChatLayout from './components/Chat/ChatLayout';
import FeedsLayout from './components/Feeds/FeedsLayout';
import PageNotFound from './components/Auth/PageNotFound';
import { Navigate, Route, Routes } from 'react-router-dom';
import PeopleLayout from './components/People/PeopleLayout';
import usePresenceService from './hooks/usePresenceService';
import DriveLayout from './components/Team/Drive/DriveLayout';
import HomePageLayout from './components/Home/HomePageLayout';
import SettingsLayout from './components/Settings/SettingsLayout';
import MoreFeaturesLayout from './components/More/MoreFeaturesLayout';
import CreateOrgModal from './components/Org/CreateOrg/CreateOrgModal';
import ManageWorkLayout from './components/Team/ManageWork/ManageWorkLayout';
import NewModuleLayout from './components/Team/ManageWork/CreateModule/NewModuleLayout';
import ModuleBuilderLayout from './components/Team/ManageWork/ModuleBuilder/ModuleBuilderLayout';

const SubscriptionNotExpiredRoutes = () => {

    const presenceService = usePresenceService();

    useEffect(() => {

        presenceService.setUserOnline();

        const interval = setInterval(presenceService.setUserOnline, 15 * 1000); // 15 seconds
        return () => clearInterval(interval);
    }, [presenceService]);

    return (
        <Routes>
            <Route path="/" element={<HomePageLayout />} />
            <Route path='/org/create' element={<CreateOrgModal />} />
            <Route path="/feeds/*" element={<FeedsLayout />} />
            <Route path="/chat/*" element={<ChatLayout />} />
            <Route path="/people/*" element={<PeopleLayout />} />
            <Route path="/team/*" element={<TeamLayout />} />
            <Route path='/store/*' element={<DriveLayout />} />
            <Route path="/manage" element={<Navigate to="/manage/module" />} />
            <Route path="/manage/module/create" element={<NewModuleLayout />} />
            <Route path="/manage/module/builder/:moduleId" element={<ModuleBuilderLayout />} />
            <Route path="/manage/module/*" element={<ManageWorkLayout />} />
            <Route path="/settings/*" element={<SettingsLayout />} />
            <Route path='/more/*' element={<MoreFeaturesLayout />} />
            <Route path="*" element={<PageNotFound />} />
        </Routes>
    )
}

export default SubscriptionNotExpiredRoutes