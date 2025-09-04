import VerticalLoaderSlice from './store/reducers/VerticalLoaderSlice';
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
import OrgSlice from './store/reducers/OrgSlice';
import DriveSlice from './store/reducers/DriveSlice';
import PeopleSlice from './store/reducers/PeopleSlice';
import ProfileSlice from './store/reducers/ProfileSlice';
import CreateOrgSlice from './store/reducers/CreateOrgSlice';
import DepartmentSlice from './store/reducers/DepartmentSlice';
import ManageUsersSlice from './store/reducers/ManageUsersSlice';
import ModuleFilterSlice from './store/reducers/ModuleFilterSlice';
import TrendingPostsSlice from './store/reducers/TrendingPostsSlice';
import ActiveChatTabSlice from './store/reducers/ActiveChatTabSlice';
import LeftNavigationSlice from './store/reducers/LeftNavigationSlice';
import SinglePostSlice from './store/reducers/SinglePostSlice';
import ConversationSlice from './store/reducers/ConversationSlice';
import ConfirmationPopupSlice from './store/reducers/ConfirmationPopupSlice';
import CurrentModuleSlice from './store/reducers/CurrentModuleSlice';
import FullScreenImageSlice from './store/reducers/FullScreenImageSlice';
import SupportTicketsSlice from './store/reducers/SupportTicketsSlice';
import CurrentChatPreviewSlice from './store/reducers/CurrentChatPreviewSlice';
import ShowChatHeaderOptionsModalSlice from './store/reducers/ShowChatHeaderOptionsModalSlice';

const store = configureStore({
    reducer: combineReducers({
        org: OrgSlice,
        popup: PopupSlice,
        drive: DriveSlice,
        posts: PostReducer,
        people: PeopleSlice,
        chatId: ChatIdSlice,
        profile: ProfileSlice,
        myProfile: MyProfileSlice,
        createOrg: CreateOrgSlice,
        singlePost: SinglePostSlice,
        department: DepartmentSlice,
        manageUsers: ManageUsersSlice,
        recentChats: RecentChatsSlice,
        Navigation: NavigationReducers,
        moduleFilter: ModuleFilterSlice,
        conversation: ConversationSlice,
        activeChatTab: ActiveChatTabSlice,
        trendingPosts: TrendingPostsSlice,
        currentModule: CurrentModuleSlice,
        supportTickets: SupportTicketsSlice,
        leftNavigation: LeftNavigationSlice,
        VerticalLoader: VerticalLoaderSlice,
        fullScreenImage: FullScreenImageSlice,
        activeRecentChat: ActiveRecentChatSlice,
        confirmationPopup: ConfirmationPopupSlice,
        pinnedDirectMessage: PinnedDirectMessageSlice,
        currentChatPreviewSlice: CurrentChatPreviewSlice,
        showChatHeaderOptionsModalSlice: ShowChatHeaderOptionsModalSlice
    }),
    devTools: true
});

export default store;