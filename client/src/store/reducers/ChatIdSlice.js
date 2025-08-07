import { createSlice } from "@reduxjs/toolkit";

const ChatIdSlice = createSlice({
    'name': 'chatId',
    'initialState': null,
    'reducers': {
        'setChatId': (state, action) => {
            return action.payload;
        }
    }
});

export const { setChatId } = ChatIdSlice.actions;
export default ChatIdSlice.reducer;