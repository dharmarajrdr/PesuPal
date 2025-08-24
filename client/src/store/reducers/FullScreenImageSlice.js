import { createSlice } from "@reduxjs/toolkit";

const FullScreenImageSlice = createSlice({
    'name': 'fullScreenImage',
    'initialState': {
        mediaUrls: [],
        currentIndex: 0
    },
    'reducers': {
        'showFullScreenImage': (state, action) => {
            state.mediaUrls = [action.payload];
        },
        'showFullScreenImages': (state, action) => {
            state.mediaUrls = [...action.payload];
        },
        'showImageAt': (state, action) => {
            state.currentIndex = action.payload;
        },
        'showFullScreenImageAt': (state, action) => {
            state.mediaUrls = [...action.payload.mediaUrls];
            state.currentIndex = action.payload.currentIndex;
        },
        'hideFullScreenImage': (state) => {
            state.mediaUrls = [];
            state.currentIndex = 0;
        }
    }
})

export const { showFullScreenImage, showFullScreenImages, hideFullScreenImage, showFullScreenImageAt, showImageAt } = FullScreenImageSlice.actions;

export default FullScreenImageSlice.reducer;