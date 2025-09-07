import Post from './Post';
import { useState } from 'react';
import { useSelector } from 'react-redux';
import PostsLikedBy from './PostsLikedBy';

const PostList = () => {

    const { list: posts } = useSelector(state => state.posts);
    const [showPostLikesById, setShowPostLikesById] = useState(null);

    return <>
        {showPostLikesById && <PostsLikedBy postId={showPostLikesById} closeShowLikesList={() => setShowPostLikesById(null)} />}
        {posts.map((post, index) => <Post key={index} post={post} setShowPostLikesById={setShowPostLikesById} />)}
    </>
}

export default PostList