import { createSlice } from "@reduxjs/toolkit";

const ConversationSlice = createSlice({
    'name': 'conversation',
    'initialState': {
        'messages': []
    },
    'reducers': {
        'setMessages': (state, action) => {
            state.messages = action.payload;
        },
        'addMessage': (state, action) => {
            state.messages.push(action.payload);
        },
        'clearMessages': (state) => {
            state.messages = [];
        }
    }
});

export const { setMessages, addMessage, clearMessages } = ConversationSlice.actions;

export default ConversationSlice.reducer;