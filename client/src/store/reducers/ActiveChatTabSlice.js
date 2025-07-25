import { createSlice } from "@reduxjs/toolkit";

const details = {
    'directMessage': {
        'name': 'directMessage',
        'chatMode': 'DIRECT_MESSAGE',
        'route': '/chat/messages',
        'pinnedMessagesApi': '/api/v1/pinned-direct-messages',
        'recentChatsApi': '/api/v1/direct-messages/recent',
        'chatPreviewApi': '/api/v1/direct-messages/preview',
        'retrieveConversationApi': '/api/v1/direct-messages',
        'readAllMessagesApi': '/api/v1/direct-messages',
        'showStatusIndicator': true
    },
    'groupMessage': {
        'name': 'groupMessage',
        'chatMode': 'GROUP_MESSAGE',
        'route': '/chat/groups',
        'pinnedMessagesApi': '/api/v1/pinned-group-messages',
        'recentChatsApi': '/api/v1/group/recent',
        'chatPreviewApi': '/api/v1/group/preview',
        'retrieveConversationApi': '/api/v1/group-chat-message',
        'readAllMessagesApi': '/api/v1/group-chat-message',
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
            return details[chatType];
        }
    }
});

export const { setActiveChatTab } = ActiveChatTabSlice.actions;
export default ActiveChatTabSlice.reducer;