import { createSlice } from "@reduxjs/toolkit";

const SinglePostSlice = createSlice({
    name: "singlePost",
    initialState: {
        post: null,
    },
    reducers: {
        showSinglePost: (state, action) => {
            state.post = action.payload;
        },
        hideSinglePost: (state) => {
            state.post = null;
        }
    }
})

export const { showSinglePost, hideSinglePost } = SinglePostSlice.actions;

export default SinglePostSlice.reducer;