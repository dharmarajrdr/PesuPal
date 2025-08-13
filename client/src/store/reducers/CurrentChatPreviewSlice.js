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
        increaseParticipantsCount: (state) => {
            if (state) {
                state.participantsCount += 1;
            }
        },
        clearCurrentChatPreview: () => {
            return null;
        }
    }
});

export const { setCurrentChatPreview, updateCurrentChatPreview, increaseParticipantsCount, clearCurrentChatPreview } = CurrentChatPreviewSlice.actions;
export default CurrentChatPreviewSlice.reducer;