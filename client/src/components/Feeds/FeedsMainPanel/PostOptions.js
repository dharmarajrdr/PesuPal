import { useState } from 'react';
import './PostOptions.css';
import PostsLikedBy from './PostsLikedBy';

const PostOptions = ({ postId }) => {

    const [showLikesList, setShowLikesList] = useState(false);

    return (
        <div className="FCSS" id="post-options">
            {showLikesList && <PostsLikedBy postId={postId} closeShowLikesList={() => setShowLikesList(false)} />}
            <div className='option' onClick={() => setShowLikesList(true)}>Show Post Likes</div>
            <div className='option'>Show Post Comments</div>
            <div className='option'>Enable Post Comments</div>
            <div className='option'>Enable Post Bookmarks</div>
        </div>
    )
}

export default PostOptions