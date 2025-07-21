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
    removePinnedDirectMessage: (state, action) => {
      console.log(action.payload);
      state.pinnedDirectMessages = state.pinnedDirectMessages.filter(
        message => message.chatId !== action.payload
      );
    }
  }
});

export const { setPinnedDirectMessages, addPinnedDirectMessage, removePinnedDirectMessage } = pinnedDirectMessageSlice.actions;

export default pinnedDirectMessageSlice.reducer;
