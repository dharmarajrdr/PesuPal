import './App.css';
import store from './store';
import { Provider } from 'react-redux';
import { useEffect, useState } from 'react';
import Signup from './components/Auth/Signup';
import Signin from './components/Auth/Signin';
import { hasCookie } from './components/Auth/utils';
import AuthModal from './components/Auth/AuthModal';
import TeamLayout from './components/Team/TeamLayout';
import ChatLayout from './components/Chat/ChatLayout';
import VerticalLoader from './components/VerticalLoader';
import FeedsLayout from './components/Feeds/FeedsLayout';
import PageNotFound from './components/Auth/PageNotFound';
import CommonContainer from './components/CommonContainer';
import PeopleLayout from './components/People/PeopleLayout';
import CreateOrgModal from './components/Org/CreateOrgModal';
import DriveLayout from './components/Team/Drive/DriveLayout';
import HomePageLayout from './components/Home/HomePageLayout';
import SettingsLayout from './components/Settings/SettingsLayout';
import MoreFeaturesLayout from './components/More/MoreFeaturesLayout';
import LeftNavigation from './components/LeftNavigation/LeftNavigation';
import ManageWorkLayout from './components/Team/ManageWork/ManageWorkLayout';
import SubscriptionPlan from './components/Settings/subscription/SubscriptionPlan';
import { Navigate, Route, Routes, useLocation, useNavigate } from 'react-router-dom';
import NewModuleLayout from './components/Team/ManageWork/CreateModule/NewModuleLayout';
import ModuleBuilderLayout from './components/Team/ManageWork/ModuleBuilder/ModuleBuilderLayout';

function App() {

    const location = useLocation();
    const navigate = useNavigate();

    const isAuthPage = ['/signin', '/signup'].includes(location.pathname);
    const inLobby = ['/', '/org/create'].includes(location.pathname);

    useEffect(() => {
        if (!hasCookie() && !isAuthPage) {
            navigate('/signin');
        }
    }, [location.pathname, navigate]);

    const [authenticated, setAuthenticated] = useState(true);
    const [isSubscriptionExpired, setIsSubscriptionExpired] = useState(false);

    return (
        <Provider store={store}>
            <div className="App FRCS">

                {!isAuthPage && <AuthModal setIsSubscriptionExpired={setIsSubscriptionExpired} setAuthenticated={setAuthenticated} />}
                <CommonContainer />
                {/* ✅ Only render LeftNavigation if not on /signin or /signup or / */}
                {!isAuthPage && !inLobby && <LeftNavigation />}
                <VerticalLoader />
                {authenticated ? null :
                    isSubscriptionExpired ?
                        <SubscriptionPlan /> :
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
                            <Route path="/signup" element={<Signup />} />
                            <Route path="/signin" element={<Signin />} />
                            <Route path="*" element={<PageNotFound />} />
                        </Routes>}
            </div>
        </Provider>
    );
}

export default App;
