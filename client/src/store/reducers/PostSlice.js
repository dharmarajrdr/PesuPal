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
        addPost: (state, action) => {
            state.list.push(action.payload);
        },
        clearPosts: (state) => {
            state.list = [];
        },
        deletePost: (state, action) => {
            state.list = state.list.filter(post => post.id !== action.payload);
        },
        setActivePostId: (state, action) => {
            state.activePostId = action.payload;
        }
    }
});

export const { setPosts, addPost, clearPosts, deletePost, setActivePostId } = postSlice.actions;
export default postSlice.reducer;
