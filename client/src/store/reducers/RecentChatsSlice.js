import { createSlice } from "@reduxjs/toolkit";

const RecentChatsSlice = createSlice({
    'name': 'recentChats',
    'initialState': [],
    'reducers': {
        'setRecentChats': (state, action) => {
            return action.payload;
        },
        'addRecentChat': (state, action) => {
            if (action.payload.length) {
                state.push(...action.payload);
            } else {
                state.push(action.payload);
            }
        },
        'removeRecentChat': (state, action) => {
            return state.filter(chat => chat.chatId !== action.payload.chatId);
        },
        'moveRecentChatToTop': (state, action) => {
            const chatIndex = state.findIndex(chat => chat.chatId === action.payload.chatId);
            if (chatIndex !== -1) {
                const [chat] = state.splice(chatIndex, 1);
                state.unshift(chat);
            }
        }
    }
});

export const { setRecentChats, addRecentChat, removeRecentChat, moveRecentChatToTop } = RecentChatsSlice.actions;
export default RecentChatsSlice.reducer;
