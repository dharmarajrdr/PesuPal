import './App.css';
import { useEffect } from 'react';
import { Route, Routes, useLocation, useNavigate } from 'react-router-dom';
import { hasCookie } from './components/Auth/utils';
import Signup from './components/Auth/Signup';
import Signin from './components/Auth/Signin';
import LeftNavigation from './components/LeftNavigation/LeftNavigation';
import FeedsLayout from './components/Feeds/FeedsLayout';
import ChatLayout from './components/Chat/ChatLayout';
import PeopleLayout from './components/People/PeopleLayout';
import TeamLayout from './components/Team/TeamLayout';
import TrackerLayout from './components/Tracker/TrackerLayout';
import { Provider } from 'react-redux';
import PageNotFound from './components/Auth/PageNotFound';
import SettingsLayout from './components/Settings/SettingsLayout';
import MoreFeaturesLayout from './components/More/MoreFeaturesLayout';
import VerticalLoader from './components/VerticalLoader';
import CommonContainer from './components/CommonContainer';
import utils from './utils';
import store from './store';

function App() {

    const location = useLocation();
    const navigate = useNavigate();

    const isAuthPage = ['/signin', '/signup'].includes(location.pathname);

    useEffect(() => {
        if (!hasCookie() && !isAuthPage) {
            navigate('/signin');
        } else if (utils.getCurrentOrgId() == null) {
            navigate('/settings');
        }
    }, [location.pathname, navigate]);

    return (
        <Provider store={store}>
            <div className="App FRCS">

                <CommonContainer />
                {/* ✅ Only render LeftNavigation if not on /signin or /signup */}
                {!isAuthPage && <LeftNavigation />}
                <VerticalLoader />

                <Routes>
                    <Route path="/feeds/*" element={<FeedsLayout />} />
                    <Route path="/chat/*" element={<ChatLayout />} />
                    <Route path="/people/*" element={<PeopleLayout />} />
                    <Route path="/team/*" element={<TeamLayout />} />
                    <Route path="/tracker" element={<TrackerLayout />} />
                    <Route path="/settings/*" element={<SettingsLayout />} />
                    <Route path='/more/*' element={<MoreFeaturesLayout />} />
                    <Route path="/signup" element={<Signup />} />
                    <Route path="/signin" element={<Signin />} />
                    <Route path="*" element={<PageNotFound />} />
                </Routes>
            </div>
        </Provider>
    );
}

export default App;
