import './App.css';
import { useEffect, useState } from 'react';
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
import { configureStore } from '@reduxjs/toolkit';
import { combineReducers } from 'redux';
import { Provider } from 'react-redux';
import { VerticalLoaderReducer } from './store/reducers/VerticalLoader';
import PinnedDirectMessageSlice from './store/reducers/PinnedDirectMessageSlice';
import { NavigationReducers } from './store/reducers/Navigation';
import PostReducer from './store/reducers/PostSlice';
import ActiveRecentChatSlice from './store/reducers/ActiveRecentChatSlice';
import RecentChatsSlice from './store/reducers/RecentChatsSlice';
import ChatIdSlice from './store/reducers/ChatIdSlice';
import ActiveChatTabSlice from './store/reducers/ActiveChatTabSlice';
import ShowChatHeaderOptionsModalSlice from './store/reducers/ShowChatHeaderOptionsModalSlice';
import CurrentChatPreviewSlice from './store/reducers/CurrentChatPreviewSlice';
import PageNotFound from './components/Auth/PageNotFound';
import SettingsLayout from './components/Settings/SettingsLayout';
import MoreFeaturesLayout from './components/More/MoreFeaturesLayout';
import VerticalLoader from './components/VerticalLoader';
import { apiRequest } from './http_request';

const store = configureStore({
    reducer: combineReducers({
        Navigation: NavigationReducers,
        recentChats: RecentChatsSlice,
        VerticalLoader: VerticalLoaderReducer,
        posts: PostReducer,
        chatId: ChatIdSlice,
        activeChatTab: ActiveChatTabSlice,
        activeRecentChat: ActiveRecentChatSlice,
        pinnedDirectMessage: PinnedDirectMessageSlice,
        showChatHeaderOptionsModalSlice: ShowChatHeaderOptionsModalSlice,
        currentChatPreviewSlice: CurrentChatPreviewSlice
    }),
    devTools: true
});

function App() {

    const location = useLocation();
    const navigate = useNavigate();

    const isAuthPage = ['/signin', '/signup'].includes(location.pathname);

    const [orgId, setOrgId] = useState(sessionStorage.getItem('org-id'));
    const [profile, setProfile] = useState({ 'id': 8, 'title': 'Me', 'route': '/profile', 'icon': 'fa-regular fa-user', 'isActive': false });

    useEffect(() => {
        if (!hasCookie() && !isAuthPage) {
            navigate('/signin');
        }
    }, [location.pathname, navigate]);

    useEffect(() => {
        if (!isAuthPage) {
            apiRequest("/api/v1/people/display-picture", "GET").then(({ data }) => {
                const updatedProfile = { ...profile, image: data, icon: null };
                setProfile(updatedProfile);
            }).catch(({ message }) => {
                console.error("Error fetching profile image:", message);
            });
        }
    }, [orgId]);

    return (
        <Provider store={store}>
            <div className="App FRCS">
                {/* ✅ Only render LeftNavigation if not on /signin or /signup */}
                {!isAuthPage && <LeftNavigation profile={profile} />}
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
