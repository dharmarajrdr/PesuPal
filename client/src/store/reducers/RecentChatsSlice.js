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
            return state.filter(({ chatId }) => chatId !== action.payload);
        },
        'moveRecentChatToTop': (state, action) => {
            const chatIndex = state.findIndex(({ chatId }) => chatId == action.payload);
            if (chatIndex !== -1) {
                const [chat] = state.splice(chatIndex, 1);
                state.unshift(chat);
            }
        },
        'updateRecentChat': (state, action) => {
            const chatIndex = state.findIndex(({ chatId }) => chatId == action.payload.chatId);
            if (chatIndex !== -1) {
                const updatedChat = {
                    ...state[chatIndex],
                    ...action.payload,
                    recentMessage: {
                        ...state[chatIndex].recentMessage,
                        ...action.payload.recentMessage
                    }
                };
                state[chatIndex] = updatedChat;
            }
        },
        'updateOrAddRecentChat': (state, action) => {
            const chatIndex = state.findIndex(({ chatId }) => chatId == action.payload.chatId);
            if (chatIndex !== -1) {
                const updatedChat = {
                    ...state[chatIndex],
                    ...action.payload,
                    recentMessage: {
                        ...state[chatIndex].recentMessage,
                        ...action.payload.recentMessage,
                    },
                };
                state[chatIndex] = updatedChat;
            } else {
                state.push(action.payload);
            }
        },
        'clearRecentChats': (state) => {
            return [];
        }
    }
});

export const { setRecentChats, addRecentChat, removeRecentChat, moveRecentChatToTop, updateRecentChat, updateOrAddRecentChat, clearRecentChats } = RecentChatsSlice.actions;
export default RecentChatsSlice.reducer;
