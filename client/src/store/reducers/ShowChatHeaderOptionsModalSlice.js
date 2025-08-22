import { createSlice } from "@reduxjs/toolkit";

const ShowChatHeaderOptionsModalSlice = createSlice({
    'name': 'chatHeaderOptionsModal',
    'initialState': false,
    'reducers': {
        'setShowChatHeaderOptionsModal': (state, action) => {
            return action.payload == true;
        }
    }
});

export const { setShowChatHeaderOptionsModal } = ShowChatHeaderOptionsModalSlice.actions;
export default ShowChatHeaderOptionsModalSlice.reducer;