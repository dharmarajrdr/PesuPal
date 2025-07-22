import { createSlice } from "@reduxjs/toolkit";

const details = {
    'directMessage': {
        'name': 'directMessage',
        'recentChatsApi': '/api/v1/direct-messages/recent',
        'showStatusIndicator': true
    },
    'groupMessage': {
        'name': 'groupMessage',
        'recentChatsApi': '/api/v1/group/recent',
        'showStatusIndicator': false
    }
}

const ActiveChatTabSlice = createSlice({
    'name': 'activeChatTab',
    'initialState': {},
    'reducers': {
        'setActiveChatTab': (state, action) => {
            const chatType = action.payload;
            if (!details[chatType]) {
                throw new Error(`Invalid chat type: ${chatType}`);
            }
            console.log(`Setting active chat tab to: ${chatType}`);
            return details[chatType];
        }
    }
});

export const { setActiveChatTab } = ActiveChatTabSlice.actions;
export default ActiveChatTabSlice.reducer;