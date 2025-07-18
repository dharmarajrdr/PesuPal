// store/slices/postSlice.js
import { createSlice } from '@reduxjs/toolkit';

export const postSlice = createSlice({
    name: 'posts',
    initialState: {
        list: [],
        activePostId: null
    },
    reducers: {
        setPosts: (state, action) => {
            state.list = action.payload;
        },
        deletePost: (state, action) => {
            state.list = state.list.filter(post => post.id !== action.payload);
        },
        setActivePostId: (state, action) => {
            state.activePostId = action.payload;
        }
    }
});

export const { setPosts, deletePost, setActivePostId } = postSlice.actions;
export default postSlice.reducer;
