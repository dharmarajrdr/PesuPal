import { createSlice } from "@reduxjs/toolkit";

const FullScreenImageSlice = createSlice({
    'name': 'fullScreenImage',
    'initialState': {
        mediaUrl: null
    },
    'reducers': {
        'showFullScreenImage': (state, action) => {
            state.mediaUrl = action.payload;
        },
        'hideFullScreenImage': (state) => {
            state.mediaUrl = null;
        }
    }
})

export const { showFullScreenImage, hideFullScreenImage } = FullScreenImageSlice.actions;

export default FullScreenImageSlice.reducer;