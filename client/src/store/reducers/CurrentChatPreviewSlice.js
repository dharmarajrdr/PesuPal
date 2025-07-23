import { createSlice } from "@reduxjs/toolkit";

const CurrentChatPreviewSlice = createSlice({
    "name": "currentChatPreview",
    "initialState": null,
    "reducers": {
        setCurrentChatPreview: (state, action) => {
            return action.payload;
        },
        clearCurrentChatPreview: () => {
            return null;
        }
    }
});

export const { setCurrentChatPreview, clearCurrentChatPreview } = CurrentChatPreviewSlice.actions;
export default CurrentChatPreviewSlice.reducer;