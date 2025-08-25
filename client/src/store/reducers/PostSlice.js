import { createSlice } from '@reduxjs/toolkit';

export const postSlice = createSlice({
    name: 'posts',
    initialState: {
        list: [],
        activePostId: null,
        currentPostData: null,
        isShowCreatePostModal: false
    },
    reducers: {
        setPosts: (state, action) => {
            state.list = action.payload;
        },
        appendPosts: (state, action) => {
            state.list = [...state.list, ...action.payload];
        },
        addPost: (state, action) => {
            state.list.push(action.payload);
        },
        clearPosts: (state) => {
            state.list = [];
            state.currentPostData = null;
        },
        deletePost: (state, action) => {
            state.list = state.list.filter(post => post.id !== action.payload);
        },
        setActivePostId: (state, action) => {
            state.activePostId = action.payload;
        },
        setPostData: (state, action) => {
            state.currentPostData = action.payload;
        },
        resetPostData: (state) => {
            state.currentPostData = null;
        },
        showCreatePostModal: (state, action) => {
            state.isShowCreatePostModal = true;
        },
        hideCreatePostModal: (state, action) => {
            state.isShowCreatePostModal = false;
        }
    }
});

export const { setPosts, appendPosts, addPost, clearPosts, deletePost, setActivePostId, setPostData, resetPostData, showCreatePostModal, hideCreatePostModal } = postSlice.actions;
export default postSlice.reducer;
