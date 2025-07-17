import { useState } from 'react';
import Post from './Post';

const PostList = ({ posts }) => {

    const [activePostId, setActivePostId] = useState(null); // only one can be open

    return <>
        {posts.map((post, index) =>
            <Post isOptionOpen={activePostId === post.id}
                onToggleOption={() => {
                    setActivePostId((prev) => (prev === post.id ? null : post.id));
                }} key={index} post={post} />)}
    </>
}

export default PostList