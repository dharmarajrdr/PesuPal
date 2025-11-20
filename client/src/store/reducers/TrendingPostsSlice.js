import { createSlice } from "@reduxjs/toolkit";

const TrendingPostsSlice = createSlice({
    name: 'trendingPosts',
    initialState: {
        posts: []
    },
    reducers: {
        setTrendingPosts: (state, action) => {
            state.posts = action.payload;
        },
        updateTrendingPost: (state, action) => {
            const updatedPost = action.payload;
            const index = state.posts.findIndex(post => post.id === updatedPost.id);
            if (index !== -1) {
                state.posts[index] = updatedPost;
            }
        }
    }
})

export const { setTrendingPosts, updateTrendingPost } = TrendingPostsSlice.actions;

export default TrendingPostsSlice.reducer;