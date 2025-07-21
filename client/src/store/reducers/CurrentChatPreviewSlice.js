import { createSlice } from "@reduxjs/toolkit";

const CurrentChatPreviewSlice = createSlice({
    "name": "currentChatPreview",
    "initialState": {},
    "reducers": {
        setCurrentChatPreview: (state, action) => {
            return action.payload;
        }
    }
});

export const { setCurrentChatPreview } = CurrentChatPreviewSlice.actions;
export default CurrentChatPreviewSlice.reducer;