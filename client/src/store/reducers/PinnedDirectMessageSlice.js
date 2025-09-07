import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    pinnedDirectMessages: []
};

const pinnedDirectMessageSlice = createSlice({
    name: 'pinnedDirectMessages',
    initialState,
    reducers: {
        setPinnedDirectMessages: (state, action) => {
            state.pinnedDirectMessages = action.payload;
        },
        addPinnedDirectMessage: (state, action) => {
            if (action.payload.length) {
                state.pinnedDirectMessages.push(...action.payload);
            } else {
                state.pinnedDirectMessages.push(action.payload);
            }
        },
        updateMemberStatusInPinnedDirectMessages: (state, action) => {
            const { userId, status } = action.payload;
            const memberIndex = state.pinnedDirectMessages.findIndex(member => member.userId === userId);
            if (memberIndex !== -1) {
                state.pinnedDirectMessages[memberIndex].status = status;
            }
        },
        updatePinnedDirectMessage: (state, action) => {
            const { chatId } = action.payload;
            const index = state.pinnedDirectMessages.findIndex(
                message => message.chatId === chatId
            );
            if (index !== -1) {
                state.pinnedDirectMessages[index] = {
                    ...state.pinnedDirectMessages[index],
                    ...action.payload
                };
            }
        },
        removePinnedDirectMessage: (state, action) => {
            state.pinnedDirectMessages = state.pinnedDirectMessages.filter(
                message => message.chatId !== action.payload
            );
        }
    }
});

export const { setPinnedDirectMessages, addPinnedDirectMessage, updatePinnedDirectMessage, updateMemberStatusInPinnedDirectMessages, removePinnedDirectMessage } = pinnedDirectMessageSlice.actions;

export default pinnedDirectMessageSlice.reducer;
