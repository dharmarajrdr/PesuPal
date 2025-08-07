import { createSlice } from "@reduxjs/toolkit";

const CurrentChatPreviewSlice = createSlice({
    "name": "currentChatPreview",
    "initialState": null,
    "reducers": {
        setCurrentChatPreview: (state, action) => {
            return action.payload;
        },
        updateCurrentChatPreview: (state, action) => {
            return { ...state, ...action.payload };
        },
        clearCurrentChatPreview: () => {
            return null;
        }
    }
});

export const { setCurrentChatPreview, updateCurrentChatPreview, clearCurrentChatPreview } = CurrentChatPreviewSlice.actions;
export default CurrentChatPreviewSlice.reducer;