import { createSlice } from '@reduxjs/toolkit';

export const postSlice = createSlice({
    name: 'posts',
    initialState: {
        list: [],
        activePostId: null,
        currentPostData: null,
        isShowCreatePostModal: false,
        hasPrivilegeToCreatePost: false
    },
    reducers: {
        setPosts: (state, action) => {
            state.list = action.payload;
        },
        setHasPrivilegeToCreatePost: (state, action) => {
            state.hasPrivilegeToCreatePost = action.payload;
        },
        appendPosts: (state, action) => {
            state.list = [...state.list, ...action.payload];
        },
        addPost: (state, action) => {
            state.list.push(action.payload);
        },
        updatePost: (state, action) => {
            const index = state.list.findIndex(post => post.id === action.payload.id);
            if (index !== -1) {
                state.list[index] = action.payload;
            }
        },
        patchPost: (state, action) => {
            const index = state.list.findIndex(post => post.id === action.payload.id);
            if (index !== -1) {
                state.list[index] = { ...state.list[index], ...action.payload };
            }
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

export const { setPosts, setHasPrivilegeToCreatePost, appendPosts, addPost, updatePost, patchPost, clearPosts, deletePost, setActivePostId, setPostData, resetPostData, showCreatePostModal, hideCreatePostModal } = postSlice.actions;
export default postSlice.reducer;
