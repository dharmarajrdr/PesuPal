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
import LeftNavigationSlice from './store/reducers/LeftNavigationSlice';
import ActiveChatTabSlice from './store/reducers/ActiveChatTabSlice';
import ShowChatHeaderOptionsModalSlice from './store/reducers/ShowChatHeaderOptionsModalSlice';
import CurrentChatPreviewSlice from './store/reducers/CurrentChatPreviewSlice';

const store = configureStore({
    reducer: combineReducers({
        Navigation: NavigationReducers,
        recentChats: RecentChatsSlice,
        VerticalLoader: VerticalLoaderReducer,
        posts: PostReducer,
        popup: PopupSlice,
        chatId: ChatIdSlice,
        myProfile: MyProfileSlice,
        activeChatTab: ActiveChatTabSlice,
        leftNavigation: LeftNavigationSlice,
        activeRecentChat: ActiveRecentChatSlice,
        pinnedDirectMessage: PinnedDirectMessageSlice,
        showChatHeaderOptionsModalSlice: ShowChatHeaderOptionsModalSlice,
        currentChatPreviewSlice: CurrentChatPreviewSlice
    }),
    devTools: true
});

export default store;