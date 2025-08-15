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
        },
        'updateMessage': (state, action) => {
            const { id, updatedMessage } = action.payload;
            state.messages = state.messages.map(msg => {
                if (msg.id === id) {
                    return { ...msg, ...updatedMessage };
                }
                return msg;
            });
        },
        'deleteMessage': (state, action) => {
            const { id } = action.payload;
            state.messages = state.messages.map(msg => {
                if (msg.id === id) {
                    return { ...msg, status: 'DELETED' };
                }
                return msg;
            });
        },
        'reactMessage': (state, action) => {
            const { id, reaction } = action.payload;
            const message = state.messages.find(msg => msg.id === id);
            if (message) {
                message.reactions[reaction] = (message.reactions[reaction] || 0) + 1;
            }
        }
    }
});

export const { setMessages, addMessage, clearMessages, updateMessage, deleteMessage, reactMessage } = ConversationSlice.actions;

export default ConversationSlice.reducer;