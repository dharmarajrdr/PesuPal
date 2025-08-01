import { VerticalLoaderReducer } from './store/reducers/VerticalLoader';
import PinnedDirectMessageSlice from './store/reducers/PinnedDirectMessageSlice';
import { NavigationReducers } from './store/reducers/Navigation';
import PostReducer from './store/reducers/PostSlice';
import ActiveRecentChatSlice from './store/reducers/ActiveRecentChatSlice';
import RecentChatsSlice from './store/reducers/RecentChatsSlice';
import ChatIdSlice from './store/reducers/ChatIdSlice';
import MyProfileSlice from './store/reducers/MyProfileSlice';
import PopupSlice from './store/reducers/PopupSlice';
import { configureStore } from '@reduxjs/toolkit';
import { combineReducers } from 'redux';
import CurrentOrgSlice from './store/reducers/CurrentOrgSlice';
import ModuleFilterSlice from './store/reducers/ModuleFilterSlice';
import ActiveChatTabSlice from './store/reducers/ActiveChatTabSlice';
import LeftNavigationSlice from './store/reducers/LeftNavigationSlice';
import CurrentModuleSlice from './store/reducers/CurrentModuleSlice';
import CurrentChatPreviewSlice from './store/reducers/CurrentChatPreviewSlice';
import ShowChatHeaderOptionsModalSlice from './store/reducers/ShowChatHeaderOptionsModalSlice';

const store = configureStore({
    reducer: combineReducers({
        Navigation: NavigationReducers,
        recentChats: RecentChatsSlice,
        VerticalLoader: VerticalLoaderReducer,
        posts: PostReducer,
        popup: PopupSlice,
        chatId: ChatIdSlice,
        myProfile: MyProfileSlice,
        currentOrg: CurrentOrgSlice, 
        moduleFilter: ModuleFilterSlice,
        activeChatTab: ActiveChatTabSlice,
        currentModule: CurrentModuleSlice,
        leftNavigation: LeftNavigationSlice,
        activeRecentChat: ActiveRecentChatSlice,
        pinnedDirectMessage: PinnedDirectMessageSlice,
        showChatHeaderOptionsModalSlice: ShowChatHeaderOptionsModalSlice,
        currentChatPreviewSlice: CurrentChatPreviewSlice
    }),
    devTools: true
});

export default store;