import { createSlice } from "@reduxjs/toolkit";

const ActiveRecentChatSlice = createSlice({
    'name': 'activeRecentChat',
    'initialState': {},
    'reducers': {
        'setActiveRecentChat': (state, action) => {
            state.activeRecentChat = action.payload;
        }
    }
});

export const { setActiveRecentChat } = ActiveRecentChatSlice.actions;
export default ActiveRecentChatSlice.reducer;