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
import ConversationSlice from './store/reducers/ConversationSlice';
import ConfirmationPopupSlice from './store/reducers/ConfirmationPopupSlice';
import CurrentModuleSlice from './store/reducers/CurrentModuleSlice';
import SupportTicketsSlice from './store/reducers/SupportTicketsSlice';
import CurrentChatPreviewSlice from './store/reducers/CurrentChatPreviewSlice';
import ShowChatHeaderOptionsModalSlice from './store/reducers/ShowChatHeaderOptionsModalSlice';

const store = configureStore({
    reducer: combineReducers({
        popup: PopupSlice,
        posts: PostReducer,
        chatId: ChatIdSlice,
        myProfile: MyProfileSlice,
        currentOrg: CurrentOrgSlice,
        recentChats: RecentChatsSlice,
        Navigation: NavigationReducers,
        moduleFilter: ModuleFilterSlice,
        conversation: ConversationSlice,
        activeChatTab: ActiveChatTabSlice,
        currentModule: CurrentModuleSlice,
        supportTickets: SupportTicketsSlice,
        leftNavigation: LeftNavigationSlice,
        VerticalLoader: VerticalLoaderReducer,
        activeRecentChat: ActiveRecentChatSlice,
        confirmationPopup: ConfirmationPopupSlice,
        pinnedDirectMessage: PinnedDirectMessageSlice,
        currentChatPreviewSlice: CurrentChatPreviewSlice,
        showChatHeaderOptionsModalSlice: ShowChatHeaderOptionsModalSlice
    }),
    devTools: true
});

export default store;